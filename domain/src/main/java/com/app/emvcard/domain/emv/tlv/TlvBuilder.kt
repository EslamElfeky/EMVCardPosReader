package com.app.emvcard.domain.emv.tlv

import com.app.emvcard.domain.emv.TlvNode
import com.app.emvcard.domain.emv.hexToBytes
import java.io.ByteArrayOutputStream

object TlvBuilder {
    fun build (nodes:List<TlvNode>): ByteArray{
        val stream= ByteArrayOutputStream()
        for (node in nodes){
            stream.write(buildNode(node))
        }
        return stream.toByteArray()
    }
    fun buildTag(tagHex: String,value: ByteArray): ByteArray{
        val tagBytes=tagHex.hexToBytes()
        val lenByte=encodeLength(value.size)
        return tagBytes+lenByte+value
    }
    private fun buildNode(node: TlvNode): ByteArray{
        val valueBytes=if (node.isConstructed && node.children.isNotEmpty()) {
            build(node.children)
        }else{
                node.value
            }
        val lenBytes=encodeLength(valueBytes.size)
        return node.tag.rowBytes+lenBytes+valueBytes
        }

    private fun encodeLength(length: Int): ByteArray =when {
        length <0x80 -> byteArrayOf(length.toByte())
        length <=0xFF ->byteArrayOf(0x81.toByte(), length.toByte())
        else -> byteArrayOf(0x82.toByte(),(length ushr 8).toByte(),(length and 0xFF).toByte())
    }

}