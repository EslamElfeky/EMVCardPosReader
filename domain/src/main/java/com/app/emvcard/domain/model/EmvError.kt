package com.app.emvcard.domain.model

sealed class EmvError(message: String) : Exception(message) {
    data object CardRemoved : EmvError("Card removed before transaction completed")
    data object Timeout : EmvError("Timeout waiting for card presentation or PIN entry")
    data class HostUnreachable(val detail: String) : EmvError("Authorization host unreachable: $detail")
    data class KernelRejected(val reason: String) : EmvError("EMV Kernel rejected transaction: $reason")
    data class HardwareFailure(val code: Int) : EmvError("Hardware peripheral failure code: $code")
}