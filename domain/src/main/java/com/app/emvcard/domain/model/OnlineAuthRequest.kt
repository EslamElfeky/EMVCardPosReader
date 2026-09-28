package com.app.emvcard.domain.model

data class OnlineAuthRequest(
    val maskedPan: String,
    val amountOfMinorUnits: Long,
    val de55TlvBytes: ByteArray,
    val systemTraceAuditNumber: String

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OnlineAuthRequest) return false

        return maskedPan==other.maskedPan &&
                amountOfMinorUnits == other.amountOfMinorUnits &&
                de55TlvBytes.contentEquals(other.de55TlvBytes) &&
                systemTraceAuditNumber == other.systemTraceAuditNumber

    }

    override fun hashCode(): Int {
        var result = amountOfMinorUnits.hashCode()
        result = 31 * result + maskedPan.hashCode()
        result = 31 * result + systemTraceAuditNumber.hashCode()
        result = 31 * result + de55TlvBytes.contentHashCode()
        return result
    }
}
