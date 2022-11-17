package com.pizza11x.androidutilskit.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri

object LinkHelper {

    fun openExternalBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }

    private val TAG = "LINK_HELPER_TAG"
}