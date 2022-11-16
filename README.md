# Ti.Android.AppLovin
Titanium Android module for AppLovin Ads platform.

Please note that if your androidManifest has screen support set to: **android:anyDensity="false"**, any banner ads will 
display too small on high density devices.
It is not clear at this point if this is a bug with AdMob or Titanium.
In any event, you will either need to NOT set your screen support -- or set android:anyDensity="true" and adjust your app layout accordingly

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_Titanium_Modules) document 
for instructions on getting started with using this module in your application.

## Requirements

For Ti.Android.AppLovin [1.0.0](https://github.com/deckameron/Ti.Android.AppLovin/blob/main/android/dist/com.upflix.applovin-android-1.0.0.zip)
- [x] Titanium SDK 10.0.0+


## Download
You can get it [here](https://github.com/deckameron/Ti.Android.AppLovin/tree/main/android/dist)

## How to use it
First you need add this meta-data to your tiapp.xml
```xml
<android>
    <manifest>
        <application android:hardwareAccelerated="true" tools:replace="android:hardwareAccelerated">
                <meta-data xmlns:tools="http://schemas.android.com/tools" tools:replace="android:value" android:name="applovin.sdk.key" android:value="lZ2EfUPNmdDD1ESDERYDNlxUrZvTEoN_TP4nFrgtJDT0eaWIlZFRGTYMbXUczRftnsGFSZI5QMKoVhZRftrG"/>
        </application>
    </manifest>
</android>
```

You need to require the module
```javascript
var AppLovin = require("ti.android.applovin");
```

# BANNER AD
```javascript
let banner = AppLovin.createBanner({
	// Specific
	adUnitId: "edfo454ofidos",	//USE YOUR AD_UNIT ID HERE
	adBackgroundColor: "#FFFFFF",
	adHeight: 50,
	// Standar View parameters
	bottom: 0
});
window.add(banner);
```

# MEDIUM RECTANGLE AD
```javascript
// Medium Rectangles are always 300x250
let mrec = AppLovin.createMrec({
	// Specific
	adUnitId: "edfo454oduiro",	//USE YOUR AD_UNIT ID HERE
	adBackgroundColor: "#000000",
	// Standar View parameters
	bottom: 0
});
window.add(mrec);
```

# INTERSTITIAL AD
```javascript
var interstitial = AppLovin.createInterstitial({
    adUnitId : 'edfo4534fusir1', //USE YOUR AD_UNIT ID HERE
});

interstitial.addEventListener(AppLovin.AD_LOADED, function() {
    Titanium.API.warn("Interstital Ad Received");
    interstitialAd.show();
});

interstitial.addEventListener(AppLovin.AD_HIDDEN, function() {
    Titanium.API.warn("Interstital ad close successfully. RIP!");
    interstitial.load();
});
```

# REWARDED
```javascript
var rewarded = AppLovin.createRewarded({
    adUnitId: 'edfa384fcsdr3', //USE YOUR AD_UNIT ID HERE
});

rewarded.addEventListener(AppLovin.AD_LOADED, function(e) {
    Titanium.API.info("Rewarded Ad AD_LOADED");
    rewarded.show();
});

rewarded.addEventListener(AppLovin.USER_REWARDED, function(e) {
    Titanium.API.info("Rewarded Ad USER_REWARDED");
    Titanium.API.info("Yay! You can give the user his reward now!");
    rewarded.load();
});
```

# Events

|Events                |Description                          |
|----------------|-------------------------------|
|_AD_LOADED_				|   Ad have successfully loaded.
|_AD_LOAD_FAILED_    				| 	Ad failed to load.
|_AD_DISPLAYED_   | 	Ad had been successfully displayed after loading.
|_AD_HIDDEN_                | 	Ad has been successfully hidden.
|_AD_DISPLAY_FAILED_                | 	Ad failed to be displayed after loading.
|_AD_CLICKED_ 				| 	Clicked has been registered by AppLovin
|_AD_EXPANDED_	|  	Some ads expand when clicked, and this is the successfull callback for it.  
|_AD_COLLAPSED_    	|	Fired after a expanded ad have been closed and colapsed.
|_AD_NOT_READY_ | 	Fired when you try to show an Interstitial or Rewarded ad that haven't been lodaded yet.
|_REWARDED_VIDEO_STARTED_              |   Fires when the rewarded video starts
|_USER_REWARDED_              |   Rewared granted! You can now rewarded the user
|_REWARDED_VIDEO_COMPLETED_              |   Fires when the rewarded video ends


# [Obtaining Consent](https://dash.applovin.com/documentation/mediation/android/getting-started/privacy) 

When you use the AppLovin SDK, you are responsible for maintaining compliance with applicable privacy regulations. If you collect and/or transmit personally identifiable information on your own, you are responsible for protecting and managing that data. Similarly, you are fully responsible for correctly collecting and passing consent values and age-related flags to AppLovin, whether you are using your own consent mechanism or a third-party API. AppLovin provides developer APIs for passing applicable consent and age-related flags.
Best practices:
- Solicit your own legal advice. Nothing in this document should be construed as legal advice.
- Read and understand [AppLovin Policies for Publishers](https://www.applovin.com/policies-publishers/), [AppLovin’s Privacy Policy](https://www.applovin.com/privacy/), and [integration guides](https://dash.applovin.com/documentation/mediation/android/getting-started/integration) offered by AppLovin.
- List AppLovin as a third-party which collects data in your privacy policy, and include a link to AppLovin in your privacy policy.
This framework helps facilitate compliance with the General Data Protection Regulation (“GDPR”), certain California privacy requirements, and various children data restrictions under GDPR, COPPA, and App Store policies. However, consent and privacy requirements may extend beyond these circumstances and should be applied accordingly.

## METHODS

### `AppLovin.setHasUserConsent(true)`

If the user does not consent to interest-based advertising, set the user consent flag to false by calling setHasUserConsent for a particular user, and start requesting ads through the AppLovin SDK. After you set the consent value to false, AppLovin will continue to respect that value for the lifetime of your application or until the user consents to interest-based advertising.
You must ensure you have set the consent flag correctly. If you set the consent flag correctly, the “Has User Consent” value shown in the logs will be either “true” or “false.” As described above, after you set the consent value, AppLovin will continue to respect that value for the lifetime of your application or until the consent value changes.


### `AppLovin.setDoNotSell(true)`

California law may require you to display a “Do Not Sell or Share My Personal Information” link or provide other options to users located in the State of California. You must set a flag that indicates whether a California-based user has opted out of a sale or share of personal information for interest-based advertising.


### `AppLovin.setIsAgeRestrictedUser()`

If you know that the user does not fall within an age-restricted category (i.e., age 16 or older, or as otherwise defined by applicable laws), you must set the age-restricted user flag to false.

