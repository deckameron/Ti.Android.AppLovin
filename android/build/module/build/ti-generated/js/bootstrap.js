/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 * Warning: This file is GENERATED, and should not be modified
 */
var bootstrap = kroll.NativeModule.require("bootstrap"),
	invoker = kroll.NativeModule.require("invoker"),
	Titanium = kroll.binding("Titanium").Titanium;

function moduleBootstrap(moduleBinding) {
	function lazyGet(object, binding, name, namespace) {
		return bootstrap.lazyGet(object, binding,
			name, namespace, moduleBinding.getBinding);
	}

	var module = moduleBinding.getBinding("com.upflix.applovin.AppLovinModule")["AppLovin"];
	var invocationAPIs = module.invocationAPIs = [];
	module.apiName = "AppLovin";

	function addInvocationAPI(module, moduleNamespace, namespace, api) {
		invocationAPIs.push({ namespace: namespace, api: api });
	}

	addInvocationAPI(module, "AppLovin", "AppLovin", "createInterstitial");addInvocationAPI(module, "AppLovin", "AppLovin", "createBanner");addInvocationAPI(module, "AppLovin", "AppLovin", "createRewarded");addInvocationAPI(module, "AppLovin", "AppLovin", "createMrec");
		if (!("__propertiesDefined__" in module)) {Object.defineProperties(module, {
"Banner": {
get: function() {
var Banner =  lazyGet(this, "com.upflix.applovin.BannerProxy", "Banner", "Banner");
return Banner;
},
configurable: true
},
"Interstitial": {
get: function() {
var Interstitial =  lazyGet(this, "com.upflix.applovin.InterstitialProxy", "Interstitial", "Interstitial");
return Interstitial;
},
configurable: true
},
"Mrec": {
get: function() {
var Mrec =  lazyGet(this, "com.upflix.applovin.MrecProxy", "Mrec", "Mrec");
return Mrec;
},
configurable: true
},
"Rewarded": {
get: function() {
var Rewarded =  lazyGet(this, "com.upflix.applovin.RewardedProxy", "Rewarded", "Rewarded");
return Rewarded;
},
configurable: true
},

});
module.constructor.prototype.createInterstitial = function() {
return new module["Interstitial"](arguments);
}
module.constructor.prototype.createBanner = function() {
return new module["Banner"](arguments);
}
module.constructor.prototype.createRewarded = function() {
return new module["Rewarded"](arguments);
}
module.constructor.prototype.createMrec = function() {
return new module["Mrec"](arguments);
}
}
module.__propertiesDefined__ = true;
return module;

}
exports.bootstrap = moduleBootstrap;
