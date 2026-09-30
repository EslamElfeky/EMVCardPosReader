package com.app.emvcard.domain.emv.tlv

import com.app.emvcard.domain.emv.hexToBytes
import java.io.ByteArrayOutputStream

object TlvBuilder {


    fun build(tags: Map<String, ByteArray>): ByteArray {
        val stream = ByteArrayOutputStream()
        for ((key, value) in tags) {
            val leafTagHex = if (key.contains("/")) key.substringAfterLast("/") else key
            val tagBytes = leafTagHex.hexToBytes()
            val lenBytes = encodeLength(value.size)
            stream.write(tagBytes)
            stream.write(lenBytes)
            stream.write(value)
        }
        return stream.toByteArray()
    }

    fun build(tlvMap: TlvMap): ByteArray = build(tlvMap.elements)

    fun build(nodes: List<TlvNode>): ByteArray {
        val stream = ByteArrayOutputStream()
        for (node in nodes) {
            val lenBytes = encodeLength(node.value.size)
            stream.write(node.tag.rowBytes)
            stream.write(lenBytes)
            stream.write(node.value)
        }
        return stream.toByteArray()
    }

    fun buildTag(tagHex: String, value: ByteArray): ByteArray {
        val tagBytes = tagHex.hexToBytes()
        val lenBytes = encodeLength(value.size)
        val stream = ByteArrayOutputStream(tagBytes.size + lenBytes.size + value.size)
        stream.write(tagBytes)
        stream.write(lenBytes)
        stream.write(value)
        return stream.toByteArray()
    }

    private fun encodeLength(length: Int): ByteArray = when {
        length < 0x80 -> byteArrayOf(length.toByte())
            length <= 0xFF -> byteArrayOf(0x81.toByte(), length.toByte())
            length <= 0xFFFF -> byteArrayOf(0x82.toByte(), (length ushr 8).toByte(), (length and 0xFF).toByte())
        else -> byteArrayOf(
            0x83.toByte(),
            (length ushr 16).toByte(),
            (length ushr 8).toByte(),
            (length and 0xFF).toByte()
        )
    }
}