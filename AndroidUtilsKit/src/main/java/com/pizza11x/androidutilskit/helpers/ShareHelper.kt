package com.pizza11x.androidutilskit.helpers

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

object ShareHelper {

    fun shareText(context: Context, extra: String?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, extra)
            type = "text/plain"
        }
        ContextCompat.startActivity(context, Intent.createChooser(sendIntent, null), null)
    }

    fun shareImage(
        context: Context,
        imagePath: File,
        subject: String = "",
        text: String = ""
    ) {
        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            imagePath
        )

        val sendIntent: Intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        }
        ContextCompat.startActivity(context, Intent.createChooser(sendIntent, null), null)

    }
}
