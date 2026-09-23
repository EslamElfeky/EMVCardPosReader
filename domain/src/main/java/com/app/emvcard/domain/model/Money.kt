package com.app.emvcard.domain.model

import java.util.Currency
import java.util.Locale
import kotlin.math.pow

data class Money(
    val amountOfMinorUnits: Long,
    val currency: Currency = Currency.getInstance("USD")
): Comparable<Money>{
    init {
       require(amountOfMinorUnits>=0){"Amount must be non-negative"}
       require(amountOfMinorUnits<=MAX_EMV_AMOUNT){"Amount exceeds maximum 12-digit EMV limit (\$MAX_EMV_AMOUNT)"}

    }
    val formatted: String
        get() {
            val fractionDigits = currency.defaultFractionDigits
            return if (fractionDigits > 0) {
                val divisor = 10.0.pow(fractionDigits.toDouble())
                String.format(Locale.US, "%.${fractionDigits}f %s", amountOfMinorUnits / divisor, currency.currencyCode)
            } else {
                "$amountOfMinorUnits ${currency.currencyCode}"
            }
        }

    fun toEmvNumeric(): String=amountOfMinorUnits.toString().padStart(12,'0')

    operator fun plus(other: Money): Money {
        require(this.currency == other.currency) { "Cannot add different currencies: ${currency.currencyCode} and ${other.currency.currencyCode}" }
        return Money(this.amountOfMinorUnits + other.amountOfMinorUnits, other.currency)

    }

    operator fun minus(other: Money): Money{
        require(this.currency == other.currency) { "Cannot subtract different currencies: ${currency.currencyCode} and ${other.currency.currencyCode}" }
        return Money(this.amountOfMinorUnits-other.amountOfMinorUnits,other.currency)
    }

    override fun compareTo(other: Money): Int {
       require(this.currency==other.currency){"can not compare different currencies"}
        return amountOfMinorUnits.compareTo(other.amountOfMinorUnits)
    }
    companion object{
        private const val MAX_EMV_AMOUNT=999_9999_9999_9999L
        fun zero(currencyCode: String="USD"): Money=
            Money(0L, Currency.getInstance(currencyCode))
        fun ofMinor(units: Long,currencyCode: String="USD")= Money(units, Currency.getInstance(currencyCode))


    }

}
