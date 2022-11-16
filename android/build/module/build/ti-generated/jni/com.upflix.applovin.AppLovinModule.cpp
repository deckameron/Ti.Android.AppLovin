/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2011-2018 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

/** This code is generated, do not edit by hand. **/

#include "com.upflix.applovin.AppLovinModule.h"

#include "JSException.h"
#include "TypeConverter.h"



#include "com.upflix.applovin.InterstitialProxy.h"
#include "com.upflix.applovin.BannerProxy.h"
#include "com.upflix.applovin.RewardedProxy.h"
#include "com.upflix.applovin.MrecProxy.h"

#include "org.appcelerator.kroll.KrollModule.h"

#define TAG "AppLovinModule"

using namespace v8;

namespace com {
namespace upflix {
namespace applovin {


Persistent<FunctionTemplate> AppLovinModule::proxyTemplate;
Persistent<Object> AppLovinModule::moduleInstance;
jclass AppLovinModule::javaClass = NULL;

AppLovinModule::AppLovinModule() : titanium::Proxy()
{
}

void AppLovinModule::bindProxy(Local<Object> exports, Local<Context> context)
{
	Isolate* isolate = context->GetIsolate();

	Local<FunctionTemplate> pt = getProxyTemplate(isolate);

	v8::TryCatch tryCatch(isolate);
	Local<Function> constructor;
	MaybeLocal<Function> maybeConstructor = pt->GetFunction(context);
	if (!maybeConstructor.ToLocal(&constructor)) {
		titanium::V8Util::fatalException(isolate, tryCatch);
		return;
	}

	Local<String> nameSymbol = NEW_SYMBOL(isolate, "AppLovin"); // use symbol over string for efficiency
	MaybeLocal<Object> maybeInstance = constructor->NewInstance(context);
	Local<Object> instance;
	if (!maybeInstance.ToLocal(&instance)) {
		titanium::V8Util::fatalException(isolate, tryCatch);
		return;
	}
	exports->Set(context, nameSymbol, instance);
	moduleInstance.Reset(isolate, instance);
}

void AppLovinModule::dispose(Isolate* isolate)
{
	LOGD(TAG, "dispose()");
	if (!proxyTemplate.IsEmpty()) {
		proxyTemplate.Reset();
	}
	if (!moduleInstance.IsEmpty()) {
	    moduleInstance.Reset();
	}

	titanium::KrollModule::dispose(isolate);
}

Local<FunctionTemplate> AppLovinModule::getProxyTemplate(v8::Isolate* isolate)
{
	Local<Context> context = isolate->GetCurrentContext();
	if (!proxyTemplate.IsEmpty()) {
		return proxyTemplate.Get(isolate);
	}

	LOGD(TAG, "AppLovinModule::getProxyTemplate()");

	javaClass = titanium::JNIUtil::findClass("com/upflix/applovin/AppLovinModule");
	EscapableHandleScope scope(isolate);

	// use symbol over string for efficiency
	Local<String> nameSymbol = NEW_SYMBOL(isolate, "AppLovin");

	Local<FunctionTemplate> t = titanium::Proxy::inheritProxyTemplate(
		isolate,
		titanium::KrollModule::getProxyTemplate(isolate),
		javaClass,
		nameSymbol);

	proxyTemplate.Reset(isolate, t);
	t->Set(titanium::Proxy::inheritSymbol.Get(isolate), FunctionTemplate::New(isolate, titanium::Proxy::inherit<AppLovinModule>));

	// Method bindings --------------------------------------------------------

	Local<ObjectTemplate> prototypeTemplate = t->PrototypeTemplate();
	Local<ObjectTemplate> instanceTemplate = t->InstanceTemplate();

	// Delegate indexed property get and set to the Java proxy.
	instanceTemplate->SetIndexedPropertyHandler(titanium::Proxy::getIndexedProperty,
		titanium::Proxy::setIndexedProperty);

	// Constants --------------------------------------------------------------
	JNIEnv *env = titanium::JNIScope::getEnv();
	if (!env) {
		LOGE(TAG, "Failed to get environment in AppLovinModule");
		//return;
	}


			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "REWARDED_VIDEO_STARTED", "rewarded_video_started");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_DISPLAY_FAILED", "ad_display_failed");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "USER_REWARDED", "user_rewarded");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_NOT_READY", "ad_not_ready");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_HIDDEN", "ad_hidden");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_LOADED", "ad_loaded");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_DISPLAYED", "ad_displayed");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_CLICKED", "ad_clicked");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "REWARDED_VIDEO_COMPLETED", "rewarded_video_completed");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_LOAD_FAILED", "ad_load_failed");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_EXPANDED", "ad_expanded");

			DEFINE_STRING_CONSTANT(isolate, prototypeTemplate, "AD_COLLAPSED", "ad_collapsed");


	// Dynamic properties -----------------------------------------------------

	// Accessors --------------------------------------------------------------

	return scope.Escape(t);
}

Local<FunctionTemplate> AppLovinModule::getProxyTemplate(v8::Local<v8::Context> context)
{
	return getProxyTemplate(context->GetIsolate());
}

// Methods --------------------------------------------------------------------

// Dynamic property accessors -------------------------------------------------


} // applovin
} // upflix
} // com
