package com.app.emvcard.domain.model

data class ReversalRequest(
    val originalStan: String,
    val maskPan:String,
    val amountOfMinorUnits: Long,
    val reasonCode: String="400"
)
