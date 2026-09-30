package com.app.emvcard.domain.emv.tlv

import com.app.emvcard.domain.emv.toHexString

data class TlvTag(val rowBytes: ByteArray) {
    val hexString: String get()=rowBytes.toHexString()
    val isConstructed: Boolean
        get()=(rowBytes[0].toInt() and 0x20)!=0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TlvTag) return false
        return rowBytes.contentEquals(other.rowBytes)
    }

    override fun hashCode(): Int { return rowBytes.contentHashCode() }
    override fun toString(): String = hexString

}
