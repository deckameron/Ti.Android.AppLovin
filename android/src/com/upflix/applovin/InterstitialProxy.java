package com.upflix.applovin;

import android.os.Handler;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;

import java.util.concurrent.TimeUnit;

@Kroll.proxy(creatableInModule = AppLovinModule.class)
public class InterstitialProxy extends KrollProxy implements MaxAdListener {

    private final String TAG = "InterstitialAd";

    private MaxInterstitialAd interstitialAd;
    private int retryAttempt;

    public InterstitialProxy() {
        super();
        Log.d(TAG, "createInterstitial()");
    }

    // Handle creation options
    @Override
    public void handleCreationDict(KrollDict options) {
        Log.d(TAG, "handleCreationDict...");
        super.handleCreationDict(options);
        if (options.containsKeyAndNotNull("adUnitId")) {
            AppLovinModule.AD_UNIT_ID = options.getString("adUnitId");
            Log.d(TAG, "adUnitId: " + AppLovinModule.AD_UNIT_ID);

            createInterstitialAd(AppLovinModule.AD_UNIT_ID);
        }
    }

    void createInterstitialAd(String AD_UNIT_ID) {
        interstitialAd = new MaxInterstitialAd( AD_UNIT_ID, getActivity() );
        interstitialAd.setListener( InterstitialProxy.this );
    }

    @Kroll.method
    public void load(){
        interstitialAd.loadAd();
    }

    @Kroll.method
    public void show(){
        if ( interstitialAd.isReady() ) {
            interstitialAd.showAd();
        } else {
            if (hasListeners(AppLovinModule.AD_NOT_READY)) {
                fireEvent(AppLovinModule.AD_NOT_READY, new KrollProxy());
            }
        }
    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'
        // Reset retry attempt
        retryAttempt = 0;
        if (hasListeners(AppLovinModule.AD_LOADED)) {
            KrollDict dictCallback = new KrollDict();
            dictCallback.put("adUnitId", ad.getAdUnitId());
            dictCallback.put("revenue", ad.getRevenue());
            dictCallback.put("height", ad.getSize().getHeight());
            dictCallback.put("width", ad.getSize().getWidth());
            dictCallback.put("placement", ad.getPlacement());
            fireEvent(AppLovinModule.AD_LOADED, dictCallback);
        }

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {
        if (hasListeners(AppLovinModule.AD_DISPLAYED)) {
            fireEvent(AppLovinModule.AD_DISPLAYED, new KrollProxy());
        }
    }

    @Override
    public void onAdHidden(MaxAd ad) {
        if (hasListeners(AppLovinModule.AD_HIDDEN)) {
            fireEvent(AppLovinModule.AD_HIDDEN, new KrollProxy());
            Log.d(TAG, "You can load another Interstitial now.");
        }
    }

    @Override
    public void onAdClicked(MaxAd ad) {
        if (hasListeners(AppLovinModule.AD_CLICKED)) {
            fireEvent(AppLovinModule.AD_CLICKED, new KrollProxy());
        }
    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {
        // Interstitial ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)

        retryAttempt++;
        long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                interstitialAd.loadAd();
            }
        }, delayMillis );

        if (hasListeners(AppLovinModule.AD_LOAD_FAILED)) {
            KrollDict errorCallback = new KrollDict();
            errorCallback.put("adUnitId", adUnitId);
            errorCallback.put("code", error.getCode());
            errorCallback.put("network_error_code", error.getMediatedNetworkErrorCode());
            errorCallback.put("network_error_message", error.getMediatedNetworkErrorMessage());
            errorCallback.put("message", error.getMessage());
            fireEvent(AppLovinModule.AD_LOAD_FAILED, errorCallback);
        }
    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
        interstitialAd.loadAd();
        if (hasListeners(AppLovinModule.AD_DISPLAY_FAILED)) {
            KrollDict errorCallback = new KrollDict();
            errorCallback.put("code", error.getCode());
            errorCallback.put("network_error_code", error.getMediatedNetworkErrorCode());
            errorCallback.put("network_error_message", error.getMediatedNetworkErrorMessage());
            errorCallback.put("message", error.getMessage());
            fireEvent(AppLovinModule.AD_DISPLAY_FAILED, errorCallback);
        }
    }
}
