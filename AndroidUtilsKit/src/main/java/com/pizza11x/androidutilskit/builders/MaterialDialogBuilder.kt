package com.pizza11x.androidutilskit.builders

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object MaterialDialogBuilder {

    /* PUBLIC FUN */
    fun show(
        context: Context,
        title: String,
        message: String,
        positiveMessage: String? = null,
        negativeMessage: String? = null,
        positiveListener: (() -> Unit)? = null,
        negativeListener: (() -> Unit)? = null,
    ) {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)

        positiveMessage?.let { it ->
            builder.setPositiveButton(it) { dialog, _ ->
                dialog.dismiss()
                positiveListener?.invoke()
            }
        }


        negativeMessage?.let { it ->
            builder.setNegativeButton(it) { dialog, _ ->
                dialog.dismiss()
                negativeListener?.invoke()
            }
        }
        
        builder.show()
    }
}