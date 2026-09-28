package com.app.emvcard.domain.model

data class TerminalStatus(
    val isConnected: Boolean,
    val model: String,
    val serialNumber: String,
    val batteryPercentage: Int
)
