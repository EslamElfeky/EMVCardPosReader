package com.app.emvcard.domain.emv.tlv

import com.app.emvcard.domain.emv.hexToBytes

object TlvParser {

    val DEFAULT_OPAQUE_TEMPLATES = setOf(
        "BF0C",
        "DF40",
        "DF41"
    )

    fun parseToMap(
        data: ByteArray,
        opaqueTemplates: Set<String> = DEFAULT_OPAQUE_TEMPLATES
    ): TlvMap {
        val tlvMap = TlvMap()
        parseFlatInternal(data, 0, data.size, null, opaqueTemplates, tlvMap)
        return tlvMap
    }

    fun parse(data: ByteArray): List<TlvNode> {
        val map = parseToMap(data)
        return map.elements.map { (key, bytes) ->
            val leafTagHex = if (key.contains("/")) key.substringAfterLast("/") else key
            TlvNode(TlvTag(leafTagHex.hexToBytes()), bytes, key.substringBeforeLast("/", ""))
        }
    }

    private fun parseFlatInternal(
        data: ByteArray,
        startOffset: Int,
        limit: Int,
        parentTagHex: String?,
        opaqueTemplates: Set<String>,
        dest: TlvMap
    ) {
        var offset = startOffset

        while (offset < limit) {
            val current = data[offset].toInt() and 0xFF
            if (current == 0x00 || current == 0xFF) {
                offset++
                continue
            }

            val startTag = offset
            val firstByte = data[offset].toInt() and 0xFF
            offset++

            val isConstructed = (firstByte and 0x20) != 0

            if ((firstByte and 0x1F) == 0x1F) {
                while (offset < limit && (data[offset].toInt() and 0x80) != 0) {
                    offset++
                }
                if (offset < limit) {
                    offset++
                } else {
                    break
                }
            }

            val tagBytes = data.copyOfRange(startTag, offset)
            val tag = TlvTag(tagBytes)
            val tagHex = tag.hexString.uppercase()

            if (offset >= limit) break

            val lengthByte = data[offset].toInt() and 0xFF
            offset++

            val length: Int = when {
                lengthByte < 0x80 -> lengthByte
                    lengthByte == 0x81 -> {
                    if (offset >= limit) break
                    data[offset++].toInt() and 0xFF
                }
                lengthByte == 0x82 -> {
                    if (offset + 1 >= limit) break
                    val len = ((data[offset].toInt() and 0xFF) shl 8) or (data[offset + 1].toInt() and 0xFF)
                    offset += 2
                    len
                }
                lengthByte == 0x83 -> {
                    if (offset + 2 >= limit) break
                    val len = ((data[offset].toInt() and 0xFF) shl 16) or
                            ((data[offset + 1].toInt() and 0xFF) shl 8) or
                            (data[offset + 2].toInt() and 0xFF)
                    offset += 3
                    len
                }
                else -> break
            }

            if (offset + length > limit) break

            val valueBytes = data.copyOfRange(offset, offset + length)

            val isOpaque = opaqueTemplates.contains(tagHex)

            if (isConstructed && !isOpaque) {
                parseFlatInternal(data, offset, offset + length, tagHex, opaqueTemplates, dest)
            } else {
                dest.put(tagHex, valueBytes, parentTagHex)
            }

            offset += length
        }
    }
}