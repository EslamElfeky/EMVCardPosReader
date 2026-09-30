package com.app.emvcard.domain.emv.tlv

data class TlvNode(
    val tag: TlvTag,
    val value: ByteArray,
    val parentTagHex: String? = null
) {
    val isConstructed: Boolean get() = tag.isConstructed

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TlvNode) return false
        return tag == other.tag &&
                parentTagHex == other.parentTagHex &&
                value.contentEquals(other.value)
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + (parentTagHex?.hashCode() ?: 0)
        result = 31 * result + value.contentHashCode()
        return result
    }
}
