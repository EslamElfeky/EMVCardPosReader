package com.app.emvcard.domain.emv.apdu

import java.io.ByteArrayOutputStream

data class ApduCommand(
    val cla:Int,
    val ins:Int,
    val p1 :Int,
    val p2 : Int,
    val data : ByteArray,
    val le: Int?=null
) {

    init {
            require(cla in 0x00..0xFF) { "CLA must be an 8-bit integer (0..255), got $cla" }
            require(ins in 0x00..0xFF) { "INS must be an 8-bit integer (0..255), got $ins" }
            require(p1 in 0x00..0xFF) { "P1 must be an 8-bit integer (0..255), got $p1" }
            require(p2 in 0x00..0xFF) { "P2 must be an 8-bit integer (0..255), got $p2" }
            require(data.size <= 255) { "Short APDU data length cannot exceed 255 bytes (got ${data.size})" }
            if (le != null) {
                require(le in 0..256) { "Short APDU Le must be between 0 and 256 (got $le)" }
            }
        }

    fun toBytes(): ByteArray {
        val hasData = data.isNotEmpty()
        val hasLe = le != null

        val totalLength = 4 +
                (if (hasData) 1 + data.size else 0) +
                (if (hasLe) 1 else 0)

        val out = ByteArray(totalLength)
        out[0] = cla.toByte()
        out[1] = ins.toByte()
        out[2] = p1.toByte()
        out[3] = p2.toByte()

        var offset = 4
        if (hasData) {
            out[offset++] = data.size.toByte()
            System.arraycopy(data, 0, out, offset, data.size)
            offset += data.size
        }

        if (hasLe) {
            // ISO 7816: Le = 256 is encoded as 0x00
            out[offset] = if (le == 256) 0x00.toByte() else le.toByte()
        }

        return out
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ApduCommand) return false
        return cla == other.cla && ins == other.ins && p1 == other.p1 && p2 == other.p2 &&
                data.contentEquals(other.data) && le == other.le

    }

    override fun hashCode(): Int {
        var result = cla
        result = 31 * result + ins
        result = 31 * result + p1
        result = 31 * result + p2
        result = 31 * result + data.contentHashCode()
        result = 31 * result + (le?:0)
        return result
    }
}
