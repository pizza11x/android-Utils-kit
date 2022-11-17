package com.pizza11x.androidutilskit.helpers

import java.lang.Exception
import java.text.DecimalFormat

object MoneyHelper {
    val FORMAT_WITHOUT_DECIMAL = "##,##0"
    val FORMAT_TWO_DECIMALS = "##,##0.00"

    fun formatWithoutDecimal(
        number: Double,
        groupingSeparator: Char = POINT,
    ): String {
        return try {
            val decimalFormat = DecimalFormat(FORMAT_WITHOUT_DECIMAL)
            val symbols = decimalFormat.decimalFormatSymbols
            symbols.groupingSeparator = groupingSeparator
            decimalFormat.decimalFormatSymbols = symbols
            decimalFormat.format(number)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            ""
        }
    }

    fun formatWithTwoDecimals(
        number: Double,
        decimalSeparator: Char = COMMA,
        groupingSeparator: Char = POINT
    ): String {
        return try {
            val format = DecimalFormat(FORMAT_TWO_DECIMALS)
            val symbols = format.decimalFormatSymbols
            symbols.decimalSeparator = decimalSeparator
            symbols.groupingSeparator = groupingSeparator
            format.decimalFormatSymbols = symbols
            format.format(number)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            ""
        }
    }

    fun formatWithTwoDecimals(
        number: Float,
        decimalSeparator: Char = COMMA,
        groupingSeparator: Char = POINT
    ): String {
        return try {
            val format = DecimalFormat(FORMAT_TWO_DECIMALS)
            val symbols = format.decimalFormatSymbols
            symbols.decimalSeparator = decimalSeparator
            symbols.groupingSeparator = groupingSeparator
            format.decimalFormatSymbols = symbols
            format.format(number)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            ""
        }
    }

    fun formatEuro(amount: String, symbol: Char = EURO): String {
        val doubleValue: Double =
            try {
                amount.replace(COMMA, POINT).toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                0.0
            }
        return "${formatWithTwoDecimals(doubleValue)} $symbol"
    }

    fun addDecimals(amount: String, symbol: Char = EURO, decimalSeparator: Char = COMMA): String {
        val amountWithoutSymbol = amount.replace(symbol.toString(), EMPTY)
        return try {
            if (amountWithoutSymbol.contains(decimalSeparator)) {
                val splitArray = amountWithoutSymbol.split(decimalSeparator)
                when {
                    splitArray[1].isEmpty() -> "$amountWithoutSymbol$DOUBLE_ZERO $symbol"
                    splitArray[1].length == 1 -> "$amountWithoutSymbol$ZERO $symbol"
                    else -> "$amountWithoutSymbol $symbol"
                }
            } else {
                "$amountWithoutSymbol$decimalSeparator$DOUBLE_ZERO $symbol"
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            amountWithoutSymbol
        }
    }

    private val COMMA = ','
    private val POINT = '.'
    private val EURO = '€'
    private val USD = '$'
    private val DOUBLE_ZERO = "00"
    private val ZERO = "0"
    private val EMPTY = ""
    private val TAG = "MONEY_HELPER_TAG"

}