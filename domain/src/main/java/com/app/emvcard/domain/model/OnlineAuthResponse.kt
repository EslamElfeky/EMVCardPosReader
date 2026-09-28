package com.app.emvcard.domain.model

data class OnlineAuthResponse(
    val approved: Boolean,
    val arc : String,
    val authCode : String,
    val arpcByte: ByteArray? =null,
    val issuerScript: List<ByteArray> =emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OnlineAuthResponse

        if (approved != other.approved) return false
        if (arc != other.arc) return false
        if (authCode != other.authCode) return false
        if (!arpcByte.contentEquals(other.arpcByte)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = approved.hashCode()
        result = 31 * result + arc.hashCode()
        result = 31 * result + authCode.hashCode()
        result = 31 * result + (arpcByte?.contentHashCode() ?: 0)
        return result
    }
}
