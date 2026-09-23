package com.app.emvcard.domain.model

data class EmvCard(
    val technology: CardTechnology,
    val aid: String,
    val applicationLabel: String,
    val maskedPan: String,
    val expiryYyMm: String,
    val panSequenceNumber: String
)
