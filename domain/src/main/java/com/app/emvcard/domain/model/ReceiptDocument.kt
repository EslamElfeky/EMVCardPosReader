package com.app.emvcard.domain.model

data class ReceiptDocument(
    val merchantName: String,
    val terminalId: String,
    val lines: List<ReceiptLine>

)
