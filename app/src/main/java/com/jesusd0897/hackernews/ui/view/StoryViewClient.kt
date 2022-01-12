package com.jesusd0897.hackernews.ui.view

import android.graphics.Bitmap
import android.os.Build
import android.webkit.*
import androidx.annotation.RequiresApi

class StoryViewClient(
    val onLoading: (isLoading: Boolean) -> Unit,
    val onError: (url: String?, errorCode: Int?, message: String?) -> Unit,
) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        onLoading(true)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onLoading(false)
    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?,
    ) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        onError(failingUrl, errorCode, description)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?,
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        onError(request?.url.toString(), errorResponse?.statusCode, errorResponse?.reasonPhrase)
    }

}
