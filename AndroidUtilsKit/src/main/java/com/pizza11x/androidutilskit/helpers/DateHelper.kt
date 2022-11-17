package com.pizza11x.androidutilskit.helpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    private val calendar = Calendar.getInstance()
    val currentMillis = calendar.timeInMillis
    val currentDate = calendar.time
    val currentTimeZone = calendar.timeZone
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentYear = calendar.get(Calendar.YEAR)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val tomorrow = calendar.get(Calendar.DAY_OF_MONTH) + 1
    val yesterday = calendar.get(Calendar.DAY_OF_MONTH) + 1

    const val FORMAT_DD_MM_YYYY_WITH_SLASH = "dd/MM/yyyy"
    const val FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
    const val FORMAT_DD_MMM_YYYY = "dd MMM yyyy"
    const val FORMAT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    const val FORMAT_HH_MM = "HH:mm"

    fun getDateFromTimeInMillis(timeInMillis: Long, format: String = FORMAT_DD_MMM_YYYY): String {
        val date = Date(timeInMillis)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    fun getHoursFromTimeInMillis(timeInMillis: Long, format: String = FORMAT_HH_MM): String {
        val date = Date(timeInMillis)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }

    fun getDateFromString(dateString: String, format: String): Date? {
        val inputFormatter = SimpleDateFormat(format, Locale.getDefault())
        return try {
            inputFormatter.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun formatDate(
        dateString: String,
        inputFormat: String = FORMAT_TIMEZONE,
        outputFormat: String = FORMAT_DD_MMM_YYYY
    ): String {
        return try {
            val inputDate = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(dateString)
            val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
            outputFormatter.format(inputDate as Date)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun formatDate(
        date: Date,
        outputFormat: String = FORMAT_DD_MMM_YYYY
    ): String {
        return try {
            val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
            outputFormatter.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun areSameDay(
        stringDate1: String,
        stringDate2: String,
        inputFormatDate1: String = FORMAT_TIMEZONE,
        inputFormatDate2: String = FORMAT_TIMEZONE,
    ): Boolean {
        val date1 = formatDate(
            stringDate1,
            inputFormat = inputFormatDate1,
            outputFormat = FORMAT_YYYY_MM_DD
        )
        val date2 = formatDate(
            stringDate2,
            inputFormat = inputFormatDate2,
            outputFormat = FORMAT_YYYY_MM_DD
        )
        return date1 == date2
    }

    fun areSameDay(
        date1: Date,
        date2: Date,
    ): Boolean {
        val formattedDate1 = formatDate(
            date1,
            outputFormat = FORMAT_YYYY_MM_DD
        )
        val formattedDate2 = formatDate(
            date2,
            outputFormat = FORMAT_YYYY_MM_DD
        )
        return formattedDate1 == formattedDate2
    }




    private val TAG = "DATE_HELPER_TAG"
    private val FORMATTING_ERROR = "FORMATTING ERROR"
    private val CONVERSION_ERROR = "CONVERSION ERROR"
}