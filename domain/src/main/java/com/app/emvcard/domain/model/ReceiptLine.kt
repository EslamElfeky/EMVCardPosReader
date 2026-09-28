package com.app.emvcard.domain.model

data class ReceiptLine(
    val left: String,
    val right: String? = null,
    val emphasis: Boolean = false
)
