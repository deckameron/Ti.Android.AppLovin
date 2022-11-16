package com.upflix.applovin;

import android.graphics.Color;
import android.util.Log;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

public class MrecView extends TiUIView implements MaxAdViewAdListener {

    private static final String TAG = "AppLovin - MREC";

    private MaxAdView adView;

    static TiApplication appContext = TiApplication.getInstance();

    public MrecView(TiViewProxy proxy) {
        super(proxy);
    }

    void createMrecAd(String AD_UNIT_ID, String AD_COLOUR){

        adView = new MaxAdView( AD_UNIT_ID, MaxAdFormat.MREC, appContext );
        adView.setListener( this );

        proxy.setHeight(250);
        proxy.setWidth(300);

        // Set background or background color for MRECs to be fully functional
        adView.setBackgroundColor(Color.parseColor(AD_COLOUR));

        // Load the ad
        adView.loadAd();

        setNativeView(adView);
    }

    // MAX Ad Listener
    @Override
    public void onAdLoaded(final MaxAd maxAd) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_LOADED)) {
                proxy.fireEvent(AppLovinModule.AD_LOADED, new KrollDict());
            }
        }
    }

    @Override
    public void onAdDisplayed(MaxAd ad) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_DISPLAYED)) {
                proxy.fireEvent(AppLovinModule.AD_DISPLAYED, new KrollDict());
            }
        }
    }

    @Override
    public void onAdHidden(MaxAd ad) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_HIDDEN)) {
                proxy.fireEvent(AppLovinModule.AD_HIDDEN, new KrollDict());
            }
        }
    }

    @Override
    public void onAdLoadFailed(final String adUnitId, final MaxError error) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_LOAD_FAILED)) {
                KrollDict errorCallback = new KrollDict();
                errorCallback.put("adUnitId", adUnitId);
                errorCallback.put("code", error.getCode());
                errorCallback.put("network_error_code", error.getMediatedNetworkErrorCode());
                errorCallback.put("network_error_message", error.getMediatedNetworkErrorMessage());
                errorCallback.put("message", error.getMessage());
                proxy.fireEvent(AppLovinModule.AD_LOAD_FAILED, errorCallback);
            }
        }
    }

    @Override
    public void onAdDisplayFailed(final MaxAd maxAd, final MaxError error) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_DISPLAY_FAILED)) {
                KrollDict errorCallback = new KrollDict();
                errorCallback.put("code", error.getCode());
                errorCallback.put("network_error_code", error.getMediatedNetworkErrorCode());
                errorCallback.put("network_error_message", error.getMediatedNetworkErrorMessage());
                errorCallback.put("message", error.getMessage());
                proxy.fireEvent(AppLovinModule.AD_DISPLAY_FAILED, errorCallback);
            }
        }
    }

    @Override
    public void onAdClicked(final MaxAd maxAd) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_CLICKED)) {
                proxy.fireEvent(AppLovinModule.AD_CLICKED, new KrollDict());
            }
        }
    }

    @Override
    public void onAdExpanded(final MaxAd maxAd) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_EXPANDED)) {
                proxy.fireEvent(AppLovinModule.AD_EXPANDED, new KrollDict());
            }
        }
    }

    @Override
    public void onAdCollapsed(final MaxAd maxAd) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_COLLAPSED)) {
                proxy.fireEvent(AppLovinModule.AD_COLLAPSED, new KrollDict());
            }
        }
    }

    @Override
    public void processProperties(KrollDict d) {
        super.processProperties(d);
        Log.d(TAG, "Process properties");

        org.appcelerator.kroll.common.Log.d(TAG, "Processing properties...");

        if (d.containsKeyAndNotNull("adBackgroundColor")) {
            AppLovinModule.AD_COLOUR = (String) d.get("adBackgroundColor");
        }

        if (d.containsKeyAndNotNull("adUnitId")) {
            AppLovinModule.AD_UNIT_ID = d.getString("adUnitId");
            org.appcelerator.kroll.common.Log.d(TAG, "adUnitId: " + AppLovinModule.AD_UNIT_ID);
            createMrecAd(AppLovinModule.AD_UNIT_ID, AppLovinModule.AD_COLOUR);
        } else {
            org.appcelerator.kroll.common.Log.e(TAG, "You need to set the 'adUnitId' for the ad to work.");
        }
    };
}
