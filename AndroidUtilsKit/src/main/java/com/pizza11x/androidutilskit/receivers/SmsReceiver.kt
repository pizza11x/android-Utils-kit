package com.pizza11x.androidutilskit.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Pattern

class SmsReceiver : BroadcastReceiver() {

    private var otpListener: OtpReceiveListener? = null

    fun setOtpListener(listener: OtpReceiveListener) {
        this.otpListener = listener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        // CHECK ACTION
        if (intent?.action == SmsRetriever.SMS_RETRIEVED_ACTION) {
            val extras = intent.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status?
            status?.let {
                when (it.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        // Get Sms
                        val sms = extras?.getString(SmsRetriever.EXTRA_SMS_MESSAGE)
                        sms?.let { smsMessage ->
                            // Get Otp value from the sms
                            val pattern = Pattern.compile("\\d+")
                            val match = pattern.matcher(smsMessage)
                            if (match.find()) {
                                val otp = match.group()
                                otpListener?.onOtpReceived(otp)
                            }
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                        otpListener?.onTimeout()
                    }
                    else -> {
                        otpListener?.onOtherCases()
                    }
                }
            }
        }
    }

    interface OtpReceiveListener {
        fun onOtpReceived(otp: String?)
        fun onTimeout()
        fun onOtherCases()
    }
}

