package com.appodeal.appodeal_flutter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.appodeal.ads.Appodeal
import com.appodeal.ads.nativead.NativeAdView
import com.appodeal.appodeal_flutter.native_params.CustomParams
import com.appodeal.appodeal_flutter.native_params.NativeParams
import com.appodeal.appodeal_flutter.native_params.ParserUtils.parseNativeParams
import com.appodeal.appodeal_flutter.native_params.TemplateParams
import com.appodeal.appodeal_flutter.native_params.TemplateType
import com.appodeal.appodeal_flutter.native_params.ViewBuilder
import io.flutter.plugin.platform.PlatformView

class AppodealNativeAdView(activity: Activity, arguments: HashMap<*, *>) : PlatformView {
    private val placement: String = arguments["placement"] as? String ?: "default"
    private val params: NativeParams? =
        (arguments["nativeAd"] as Map<*, *>?).parseNativeParams(context = activity).apply {
            println(toString())
        }
    private val adView: NativeAdView = getAdView(activity)

    init {
        (adView.parent as? ViewGroup)?.removeView(adView)
    }

    private fun getAdView(context: Context): NativeAdView {
        val adView = if (params is TemplateParams) {
            when (params.templateType) {
                TemplateType.CONTENT_STREAM -> ViewBuilder.buildContentStream(
                    context = context,
                    adChoicePosition = params.adChoicePosition,
                    params = params
                )
                TemplateType.APP_WALL -> ViewBuilder.buildAppWall(
                    context = context,
                    adChoicePosition = params.adChoicePosition,
                    params = params
                )
                TemplateType.NEWS_FEED -> ViewBuilder.buildNewsFeed(
                    context = context,
                    adChoicePosition = params.adChoicePosition,
                    params = params
                )
            }
        } else if (params is CustomParams) {
            ViewBuilder.buildCustomAdView(
                context = context,
                adChoicePosition = params.adChoicePosition,
                params = params
            )
        } else {
            error("Error during NativeAdView creation. NativeParams cannot be null")
        }
        Appodeal.getNativeAds(1).firstOrNull()?.let {
            adView.registerView(it, placement)
        }
        return adView
    }

    override fun getView(): View = adView

    override fun dispose() {}
}


