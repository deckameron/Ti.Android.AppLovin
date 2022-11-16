package com.upflix.applovin;

import android.graphics.Color;
import android.util.Log;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdkUtils;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

public class BannerView extends TiUIView implements MaxAdViewAdListener {

    private static final String TAG = "AppLovin - Banner";
    static TiApplication appContext = TiApplication.getInstance();

    private MaxAdView adView;
    private int ad_height;

    public BannerView(TiViewProxy proxy) {
        super(proxy);
        Log.d(TAG, "Creating Banner Ad View...");
        ad_height = 50;
    }

    public void createBannerAd(String AD_UNIT_ID, String AD_COLOUR) {

        adView = new MaxAdView( AD_UNIT_ID, appContext );
        adView.setListener( this );

        proxy.setHeight(ad_height);

        // Set background or background color for banners to be fully functional
        adView.setBackgroundColor(Color.parseColor(AD_COLOUR));

        adView.loadAd();

        setNativeView(adView);
    }

    // MAX Ad Listener
    @Override
    public void onAdLoaded(final MaxAd maxAd) {
        if (proxy != null) {
            if (proxy.hasListeners(AppLovinModule.AD_LOADED)) {

                AppLovinSdkUtils.Size adViewSize = maxAd.getSize();

                int widthDp = adViewSize.getWidth();
                int heightDp = adViewSize.getHeight();

                KrollDict dictCallback = new KrollDict();
                dictCallback.put("ad_width", widthDp);
                dictCallback.put("ad_height", heightDp);

                proxy.fireEvent(AppLovinModule.AD_LOADED, dictCallback);
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

        if (d.containsKeyAndNotNull("adHeight")) {
            ad_height = (int) d.get("adHeight");

            if (ad_height != 50 && ad_height != 90){
                Log.w(TAG, "Banner height should be 50 (phones) or 90 (tablets).");
            }
        }

        if (d.containsKeyAndNotNull("adBackgroundColor")) {
            AppLovinModule.AD_COLOUR = (String) d.get("adBackgroundColor");
        }

        if (d.containsKeyAndNotNull("adUnitId")) {
            AppLovinModule.AD_UNIT_ID = d.getString("adUnitId");
            org.appcelerator.kroll.common.Log.d(TAG, "adUnitId: " + AppLovinModule.AD_UNIT_ID);
            createBannerAd(AppLovinModule.AD_UNIT_ID, AppLovinModule.AD_COLOUR);
        } else {
            org.appcelerator.kroll.common.Log.e(TAG, "You need to set the 'adUnitId' for the ad to work.");
        }
    };
}