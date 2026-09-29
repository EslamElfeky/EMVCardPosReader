package com.app.emvcard.domain.emv.tlv

object TlvDictionary {
    private val tags = mapOf(
        "4F" to "Application Identifier (AID)",
        "50" to "Application Label",
        "57" to "Track 2 Equivalent Data",
        "5A" to "Application Primary Account Number (PAN)",
        "5F20" to "Cardholder Name",
        "5F24" to "Application Expiration Date",
        "5F25" to "Application Effective Date",
        "5F28" to "Issuer Country Code",
        "5F2A" to "Transaction Currency Code",
        "5F34" to "Application PAN Sequence Number (PSN)",
        "82" to "Application Interchange Profile (AIP)",
        "84" to "Dedicated File (DF) Name",
        "8A" to "Authorization Response Code (ARC)",
        "8C" to "Card Risk Management Data Object List 1 (CDOL1)",
        "8D" to "Card Risk Management Data Object List 2 (CDOL2)",
        "8E" to "Cardholder Verification Method (CVM) List",
        "94" to "Application File Locator (AFL)",
        "95" to "Terminal Verification Results (TVR)",
        "9B" to "Transaction Status Information (TSI)",
        "9F02" to "Amount, Authorized (Numeric)",
        "9F03" to "Amount, Other (Numeric)",
        "9F08" to "Application Version Number (Card)",
        "9F09" to "Application Version Number (Terminal)",
        "9F10" to "Issuer Application Data (IAD)",
        "9F1A" to "Terminal Country Code",
        "9F26" to "Application Cryptogram (AC)",
        "9F27" to "Cryptogram Information Data (CID)",
        "9F34" to "CVM Results",
        "9F36" to "Application Transaction Counter (ATC)",
        "9F37" to "Unpredictable Number (UN)"
    )

    fun getName(tagHex: String): String = tags[tagHex.uppercase()] ?: "Tag $tagHex"
}