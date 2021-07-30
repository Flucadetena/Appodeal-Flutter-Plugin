package com.example.appodeal_flutter

import android.app.Activity
import androidx.annotation.NonNull
import com.appodeal.ads.Appodeal
import com.appodeal.ads.UserSettings
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class AppodealFlutterPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {

    private lateinit var channel: MethodChannel
    private lateinit var activity: Activity

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "appodeal_flutter")
        channel.setMethodCallHandler(this)
        Appodeal.setSharedAdsInstanceAcrossActivities(true)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "initialize" -> initialize(call, result)
            "updateConsent" -> updateConsent(call, result)
            "isInitialized" -> isInitialized(call, result)
            "isAutoCacheEnabled" -> isAutoCacheEnabled(call, result)
            "cache" -> cache(call, result)
            "show" -> show(call, result)
            "showWithPlacement" -> showWithPlacement(call, result)
            "hide" -> hide(call, result)
            "setAutoCache" -> setAutoCache(call, result)
            "setTriggerOnLoadedOnPrecache" -> setTriggerOnLoadedOnPrecache(call, result)
            "setSharedAdsInstanceAcrossActivities" -> setSharedAdsInstanceAcrossActivities(call, result)
            "isLoaded" -> isLoaded(call, result)
            "isPrecache" -> isPrecache(call, result)
            "setSmartBanners" -> setSmartBanners(call, result)
            "setTabletBanners" -> setTabletBanners(call, result)
            "setBannerAnimation" -> setBannerAnimation(call, result)
            "setBannerRotation" -> setBannerRotation(call, result)
            "trackInAppPurchase" -> trackInAppPurchase(call, result)
            "disableNetwork" -> disableNetwork(call, result)
            "disableNetworkForSpecificAdType" -> disableNetworkForSpecificAdType(call, result)
            "disableLocationPermissionCheck" -> disableLocationPermissionCheck(result)
            "disableWriteExternalStoragePermissionCheck" -> disableWriteExternalStoragePermissionCheck(result)
            "setUserId" -> setUserId(call, result)
            "setUserAge" -> setUserAge(call, result)
            "setUserGender" -> setUserGender(call, result)
            "setTesting" -> setTesting(call, result)
            "setLogLevel" -> setLogLevel(call, result)
            "setCustomFilterString" -> setCustomFilterString(call, result)
            "setCustomFilterInt" -> setCustomFilterInt(call, result)
            "setCustomFilterDouble" -> setCustomFilterDouble(call, result)
            "setCustomFilterBool" -> setCustomFilterBool(call, result)
            "canShow" -> canShow(call, result)
            "canShowWithPlacement" -> canShowWithPlacement(call, result)
            "muteVideosIfCallsMuted" -> muteVideosIfCallsMuted(call, result)
            "setChildDirectedTreatment" -> setChildDirectedTreatment(call, result)
            "destroy" -> destroy(call, result)

            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }
    override fun onDetachedFromActivityForConfigChanges() {}
    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {}
    override fun onDetachedFromActivity() {

    }


    private fun getAdType(adId: Int): Int {
        return when (adId) {
            1 -> Appodeal.BANNER
            2 -> Appodeal.BANNER_RIGHT
            3 -> Appodeal.BANNER_TOP
            4 -> Appodeal.BANNER_LEFT
            5 -> Appodeal.BANNER_BOTTOM
            6 -> Appodeal.NATIVE
            7 -> Appodeal.INTERSTITIAL
            8 -> Appodeal.REWARDED_VIDEO
            9 -> Appodeal.NON_SKIPPABLE_VIDEO
            else -> Appodeal.NONE
        }
    }

    private fun initialize(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val appKey = args["appKey"] as String
        @Suppress("UNCHECKED_CAST") val adTypes = args["adTypes"] as List<Int>
        val hasConsent = args["hasConsent"] as Boolean
        val ads = adTypes.fold(0) { acc, value -> acc or getAdType(value) }
        Appodeal.initialize(activity, appKey, ads, hasConsent)
        result.success(null)
    }

    private fun updateConsent(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val hasConsent = args["hasConsent"] as Boolean
        if (hasConsent) {
            Appodeal.updateConsent(hasConsent)
        }

        result.success(null)
    }

    private fun isInitialized(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        result.success(Appodeal.isInitialized(adType))
    }

    private fun isAutoCacheEnabled(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        result.success(Appodeal.isAutoCacheEnabled(adType))
    }

    private fun show(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        result.success(Appodeal.show(activity, adType))
    }

    private fun showWithPlacement(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        val placement = args["placement"] as String
        result.success(Appodeal.show(activity, adType, placement))
    }

    private fun setTesting(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val value = args["testMode"] as Boolean
        if (value) {
            Appodeal.setTesting(value)
        }
        result.success(null)
    }

    private fun setLogLevel(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        when (args["logLevel"] as Int) {
            1 -> Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.debug)
            2 -> Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.verbose)
            else -> Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.none)
        }

        result.success(null)
    }

    private fun setAutoCache(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        val autoCache = args["autoCache"] as Boolean

        Appodeal.setAutoCache(adType, autoCache)
        result.success(null)
    }

    private fun cache(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        Appodeal.cache(activity, adType)
        result.success(null)
    }

    private fun hide(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        Appodeal.hide(activity, adType)
        result.success(null)
    }

    private fun setTriggerOnLoadedOnPrecache(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        val onLoadedTriggerBoth = args["onLoadedTriggerBoth"] as Boolean
        Appodeal.setTriggerOnLoadedOnPrecache(adType, onLoadedTriggerBoth)
        result.success(null)
    }

    private fun setSharedAdsInstanceAcrossActivities(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val onLoadedTriggerBoth = args["sharedAdsInstanceAcrossActivities"] as Boolean
        Appodeal.setSharedAdsInstanceAcrossActivities(onLoadedTriggerBoth)
        result.success(null)
    }

    private fun isLoaded(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        result.success(Appodeal.isLoaded(adType))
    }

    private fun isPrecache(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        result.success(Appodeal.isPrecache(adType))
    }

    private fun setSmartBanners(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val smartBannerEnabled = args["smartBannerEnabled"] as Boolean
        Appodeal.setSmartBanners(smartBannerEnabled)
        result.success(null)
    }

    private fun setTabletBanners(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val tabletBannerEnabled = args["tabletBannerEnabled"] as Boolean
        Appodeal.set728x90Banners(tabletBannerEnabled)
        result.success(null)
    }

    private fun setBannerAnimation(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val bannerAnimationEnabled = args["bannerAnimationEnabled"] as Boolean
        Appodeal.setBannerAnimation(bannerAnimationEnabled)
        result.success(null)
    }

    private fun setBannerRotation(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val leftBannerRotation = getAdType(args["leftBannerRotation"] as Int)
        val rightBannerRotation = getAdType(args["rightBannerRotation"] as Int)
        Appodeal.setBannerRotation(leftBannerRotation, rightBannerRotation)
        result.success(null)
    }

    private fun trackInAppPurchase(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val amount = args["amount"] as Double
        val currency = args["currency"] as String
        Appodeal.trackInAppPurchase(activity, amount, currency)
        result.success(null)
    }

    private fun disableNetwork(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val network = args["network"] as String
        Appodeal.disableNetwork(activity, network);
        result.success(null)
    }

    private fun disableNetworkForSpecificAdType(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val network = args["network"] as String
        val adType = getAdType(args["adType"] as Int)
        Appodeal.disableNetwork(activity, network, adType);
        result.success(null)
    }

    private fun disableLocationPermissionCheck(result: Result) {
        Appodeal.disableLocationPermissionCheck()
        result.success(null)
    }

    private fun disableWriteExternalStoragePermissionCheck(result: Result) {
        Appodeal.disableWriteExternalStoragePermissionCheck()
        result.success(null)
    }

    private fun setUserId(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val userId = args["userId"] as String
        Appodeal.setUserId(userId);
        result.success(null)
    }

    private fun setUserAge(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val age = args["age"] as Int
        Appodeal.setUserAge(age);
        result.success(null)
    }

    private fun setUserGender(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        when (args["gender"] as Int) {
            0 -> Appodeal.setUserGender(UserSettings.Gender.OTHER)
            1 -> Appodeal.setUserGender(UserSettings.Gender.MALE)
            2 -> Appodeal.setUserGender(UserSettings.Gender.FEMALE)
        }
        result.success(null)
    }

    private fun setCustomFilterString(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val name = args["name"] as String
        val value = args["value"] as String
        Appodeal.setCustomFilter(name, value)
        result.success(null)
    }

    private fun setCustomFilterBool(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val name = args["name"] as String
        val value = args["value"] as Boolean
        Appodeal.setCustomFilter(name, value)
        result.success(null)
    }

    private fun setCustomFilterInt(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val name = args["name"] as String
        val value = args["value"] as Int
        Appodeal.setCustomFilter(name, value)
        result.success(null)
    }

    private fun setCustomFilterDouble(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val name = args["name"] as String
        val value = args["value"] as Double
        Appodeal.setCustomFilter(name, value)
        result.success(null)
    }

    private fun canShow(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        result.success(Appodeal.canShow(adType))
    }

    private fun canShowWithPlacement(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        val placement = args["placement"] as String
        result.success(Appodeal.canShow(adType, placement))
    }

    private fun muteVideosIfCallsMuted(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val value = args["value"] as Boolean
        if (value) {
            Appodeal.muteVideosIfCallsMuted(value)
        }
        result.success(null)
    }

    private fun setChildDirectedTreatment(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val value = args["value"] as Boolean
        if (value) {
            Appodeal.setChildDirectedTreatment(value)
        }
        result.success(null)
    }

    private fun destroy(call: MethodCall, result: Result) {
        val args = call.arguments as Map<*, *>
        val adType = getAdType(args["adType"] as Int)
        Appodeal.destroy(adType);
        result.success(null)
    }

}