package com.app.emvcard.domain.emv.apdu

class ApduResponse(val raw: ByteArray) {
    init {
        require(raw.size >= 2) { "APDU response must contain at least SW1 and SW2 (length: ${raw.size})" }
    }

    val sw1: Int = raw[raw.size - 2].toInt() and 0xFF
    val sw2: Int = raw[raw.size - 1].toInt() and 0xFF
    val statusWord: Int = (sw1 shl 8) or sw2

    val data: ByteArray = if (raw.size > 2) raw.copyOfRange(0, raw.size - 2) else EMPTY_BYTES

    val isSuccess: Boolean get() = statusWord == 0x9000 || sw1 == 0x61
    val isWrongLength: Boolean get() = sw1 == 0x6C

    val bytesAvailable: Int? get() = if (sw1 == 0x61) sw2 else null

    val correctLe: Int? get() = if (sw1 == 0x6C) sw2 else null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ApduResponse) return false
        return raw.contentEquals(other.raw)
    }

    override fun hashCode(): Int = raw.contentHashCode()

    override fun toString(): String = "ApduResponse(SW=%04X, dataLen=%d)".format(statusWord, data.size)

    companion object {
        private val EMPTY_BYTES = ByteArray(0)

        const val SW_SUCCESS = 0x9000
        const val SW_BYTES_REMAINING = 0x61
        const val SW_WRONG_LENGTH = 0x6C
    }
}
