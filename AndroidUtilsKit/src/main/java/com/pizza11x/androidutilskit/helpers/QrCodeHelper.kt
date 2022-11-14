package com.pizza11x.androidutilskit.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.pizza11x.androidutilskit.extensions.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

object QrCodeHelper {

    fun generateQrCode(
        data: String,
        size: Int,
    ): Bitmap? {
        var decoded: Bitmap? = null
        val sizeToPx = size.dpToPx()
        val bitmap =
            Bitmap.createBitmap(sizeToPx, sizeToPx, Bitmap.Config.RGB_565)
        CoroutineScope(Dispatchers.IO).launch {
            val qrCodeWriter = QRCodeWriter()
            try {
                val bitMatrix =
                    qrCodeWriter.encode(
                        data,
                        BarcodeFormat.QR_CODE,
                        sizeToPx,
                        sizeToPx
                    )
                for (x in 0 until sizeToPx) {
                    for (y in 0 until sizeToPx) {
                        bitmap.setPixel(
                            x,
                            y,
                            if (bitMatrix.get(x, y)) Color.BLACK
                            else Color.WHITE
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val out = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            decoded = BitmapFactory.decodeStream(ByteArrayInputStream(out.toByteArray()))
        }
        return decoded

    }
}
