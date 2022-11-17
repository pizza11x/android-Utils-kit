package com.pizza11x.androidutilskit.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

/*ACTIVITY */
fun <T : ViewDataBinding> Activity.bindLayout(layout: Int): T {
    return DataBindingUtil.setContentView(this, layout)

}

fun AppCompatActivity.closeKeyboard() {
    currentFocus?.let { closeKeyboard(it) }
}

/* FRAGMENT */
fun Fragment.openKeyboard(view: View) {
    if (view.requestFocus()) {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }
}

fun Fragment.closeKeyboard() {
    view?.let { activity?.closeKeyboard(it) }
}

/* VIEW */
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.showSnackbar(text: String, time: Int) {
    Snackbar.make(this, text, time).show()
}

fun View.showSoftKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, 0)
}

fun View.takeScreenshot(defaultColor: Int = -1): Bitmap {
    val bitmap = Bitmap.createBitmap(
        this.width, this.height, Bitmap.Config.RGB_565
    )
    val canvas = Canvas(bitmap)
    if(defaultColor != -1)
        canvas.drawColor(defaultColor)
    this.draw(canvas)
    return bitmap
}

/* IMAGE VIEW */
fun ImageView.load(@DrawableRes resId: Int) = Picasso.get().load(resId).into(this)

/* EDIT TEXT */
fun EditText.afterTextChanged(callback: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            callback.invoke(s.toString())
        }

    })
}
