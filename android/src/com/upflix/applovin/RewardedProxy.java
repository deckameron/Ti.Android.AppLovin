package com.upflix.applovin;

import android.os.Handler;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;

import java.util.concurrent.TimeUnit;

@Kroll.proxy(creatableInModule = AppLovinModule.class)
public class RewardedProxy extends KrollProxy implements MaxRewardedAdListener {

    private final String TAG = "RewardedAd";

    private MaxRewardedAd rewardedAd;
    private int retryAttempt;

    public RewardedProxy() {
        super();
        Log.d(TAG, "createRewarded()");
    }

    // Handle creation options
    @Override
    public void handleCreationDict(KrollDict options) {
        Log.d(TAG, "handleCreationDict...");
        super.handleCreationDict(options);
        if (options.containsKeyAndNotNull("adUnitId")) {
            AppLovinModule.AD_UNIT_ID = options.getString("adUnitId");
            Log.d(TAG, "adUnitId: " + AppLovinModule.AD_UNIT_ID);
            createRewardedAd(AppLovinModule.AD_UNIT_ID);
        }
    }

    @Kroll.method
    void createRewardedAd(String AD_UNIT_ID)
    {
        rewardedAd = MaxRewardedAd.getInstance( AD_UNIT_ID, getActivity() );
        rewardedAd.setListener( RewardedProxy.this );
        rewardedAd.loadAd();
    }

    @Kroll.method
    public void load(){
        rewardedAd.loadAd();
    }

    @Kroll.method
    public void show() {
        if (rewardedAd.isReady()) {
            rewardedAd.showAd();
        } else {
            if (hasListeners(AppLovinModule.AD_NOT_READY)) {
                fireEvent(AppLovinModule.AD_NOT_READY, new KrollProxy());
            }
        }
    }

    @Override
    public void onRewardedVideoStarted(MaxAd ad) {
        if (hasListeners(AppLovinModule.REWARDED_VIDEO_STARTED)) {
            fireEvent(AppLovinModule.REWARDED_VIDEO_STARTED, new KrollProxy());
        }
    }

    @Override
    public void onUserRewarded(final MaxAd ad, final MaxReward reward) {
        if (hasListeners(AppLovinModule.USER_REWARDED)) {
            KrollDict rCallback = new KrollDict();
            rCallback.put("rewarded_amount", reward.getAmount());
            rCallback.put("rewarded_label", reward.getLabel());
            fireEvent(AppLovinModule.USER_REWARDED, rCallback);
        }
    }

    @Override
    public void onRewardedVideoCompleted(MaxAd ad) {
        if (hasListeners(AppLovinModule.REWARDED_VIDEO_COMPLETED)) {
            fireEvent(AppLovinModule.REWARDED_VIDEO_COMPLETED, new KrollProxy());
        }
    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        // Rewarded ad is ready to be shown. rewardedAd.isReady() will now return 'true'

        // Reset retry attempt
        retryAttempt = 0;

        if (hasListeners(AppLovinModule.AD_LOADED)) {
            fireEvent(AppLovinModule.AD_LOADED, new KrollProxy());
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
        // rewarded ad is hidden. Pre-load the next ad
        rewardedAd.loadAd();

        if (hasListeners(AppLovinModule.AD_HIDDEN)) {
            fireEvent(AppLovinModule.AD_HIDDEN, new KrollProxy());
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
        // Rewarded ad failed to load
        // We recommend retrying with exponentially higher delays up to a maximum delay (in this case 64 seconds)

        retryAttempt++;
        long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                rewardedAd.loadAd();
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
        // Rewarded ad failed to display. We recommend loading the next ad
        rewardedAd.loadAd();

        if (getActivityProxy().hasListeners(AppLovinModule.AD_DISPLAY_FAILED)) {
            KrollDict errorCallback = new KrollDict();
            errorCallback.put("code", error.getCode());
            errorCallback.put("network_error_code", error.getMediatedNetworkErrorCode());
            errorCallback.put("network_error_message", error.getMediatedNetworkErrorMessage());
            errorCallback.put("message", error.getMessage());
            getActivityProxy().fireEvent(AppLovinModule.AD_DISPLAY_FAILED, errorCallback);
        }
    }
}
