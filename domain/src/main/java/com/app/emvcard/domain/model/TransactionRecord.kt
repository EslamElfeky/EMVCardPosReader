package com.app.emvcard.domain.model

data class TransactionRecord(
    val id : String,
    val timeStampEpochMS: Long,
    val amountOfMinorUnits: Long,
    val maskedPan : String,
    val cardLabel: String,
    val arc: String,
    val authCode: String,
    val outcome: String, // APPROVED, DECLINED, FAILED
    val aid : String


)
