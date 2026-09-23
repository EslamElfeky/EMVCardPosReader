package com.app.emvcard.domain.model

enum class CryptogramType(val cidByte:Int) {
    AAC(0x00),// Application Authentication Cryptogram - Declined
    TC(0x40), // Transaction Certificate - Approved Offline
    ARQC(0x80),// Application Request Cryptogram - Online Authorization Required
    AAR(0xC0); // AAR (Application Authorization Referral - rarely used / deprecated)
    companion object{
        fun fromCid(cid: Int): CryptogramType=when(cid and 0xC0){
            0x00 -> AAC
            0x40 -> TC
            0x80 -> ARQC
            else -> AAR
        }

    }
}