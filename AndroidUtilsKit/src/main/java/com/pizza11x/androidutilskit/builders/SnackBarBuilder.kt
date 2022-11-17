package com.pizza11x.androidutilskit.builders

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.pizza11x.androidutilskit.extensions.dpToPxFloat

object SnackBarBuilder {

    fun show(
        view: View,
        title: String,
        duration: Int = Snackbar.LENGTH_SHORT,
        applyMargin: Boolean = true,
        marginBottom: Float = 24.dpToPxFloat(),
        actionName: String? = null,
        listener: View.OnClickListener? = null,
    ) {
        val snack = Snackbar.make(
            view, title, duration
        )
        if (applyMargin) {
            snack.view.translationY = -marginBottom
        }
        actionName?.let {
            listener?.let {
                snack.setAction(actionName, listener)
            }
        }
        snack.show()
    }
}
