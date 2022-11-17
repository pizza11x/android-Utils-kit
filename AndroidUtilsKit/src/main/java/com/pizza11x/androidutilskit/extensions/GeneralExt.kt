package com.pizza11x.androidutilskit.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/* DATA BINDING */
fun <T : ViewDataBinding> bindLayout(
    inflater: LayoutInflater,
    layout: Int,
    viewGroup: ViewGroup?
): T {
    return DataBindingUtil.inflate(inflater, layout, viewGroup, false)
}

/* CONTEXT */
fun Context.closeKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/* LIVE DATA */
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(lifecycleOwner) {
        it?.let(observer)
    }
}

/* String */
fun String.toCapitalize(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

fun String.obfuscate(start: Int = 1, end: Int = this.lastIndex+1): String {
    var result = ""
    if(start >= 1 && end >= 1 && end <= this.lastIndex+1){
        val realEnd = if(end == lastIndex+1) this.lastIndex else end-1
        this.forEachIndexed { index, _ ->
            result += if (index in start-1..realEnd) "*" else this[index].toString()

        }
    }
    return result
}

fun String?.handleEmptyValue(): String {
    return if (this.isNullOrEmpty()) "" else this
}

/* BITMAP */
fun Bitmap.addOverlayToCenter(overlayBitmap: Bitmap): Bitmap {
    val bitmap2Width = overlayBitmap.width
    val bitmap2Height = overlayBitmap.height
    val marginLeft = (this.width * 0.5 - bitmap2Width * 0.5).toFloat()
    val marginTop = (this.height * 0.5 - bitmap2Height * 0.5).toFloat()
    val canvas = Canvas(this)
    canvas.drawBitmap(this, Matrix(), null)
    canvas.drawBitmap(overlayBitmap, marginLeft, marginTop, null)
    return this
}

fun Bitmap.storeToExternalStorage(): File? {
    val path = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES
    )
    val fileName: String =
        "Screenshot"+ System.currentTimeMillis().toString().replace(":", ".") + ".jpg"
    val file = File(path, fileName)
    return try {
        path.mkdirs()

        val fos = FileOutputStream(
            File(path.absolutePath+"/"+fileName)
        )
        this.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
        file
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

/* INT */
fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Int.dpToPxFloat(): Float {
    return (this * Resources.getSystem().displayMetrics.density)
}
