package com.app.emvcard.domain.emv

object TlvParser {
    fun parse (data: ByteArray):List<TlvNode>{
        val nodes =mutableListOf<TlvNode>()
        var offset=0
        while (offset < data.size){
            if (data[offset]==0x00.toByte() || data[offset] == 0xFF.toByte()) {
                offset++
                continue
            }
            val startTag=offset
            val firstByte =data[offset].toInt() and 0xFF
            offset++
            if ((firstByte and 0x1F)==0x1F){
            while (offset < data.size && (data[offset].toInt() and 0x80)!=0){
                offset++
            }
                if(offset < data.size){offset++}
        }
            val tagBytes=data.copyOfRange(startTag,offset)
            val tag= TlvTag(tagBytes)
            if (offset>data.size){break}
            val lengthByte=data[offset].toInt() and 0xFF
            offset++
            val length: Int=when{
                lengthByte<0x80 ->lengthByte
                lengthByte == 0x81 ->{
                    if (offset>=data.size){break}
                    val len=data[offset].toInt() and 0xFF
                    offset++
                    len
                }
                lengthByte== 0x82 ->
                {
                    if (offset+1>=data.size) {break}
                    val len =((data[offset].toInt() and 0xFF) shl 8) or (data[offset + 1].toInt() and 0xFF)
                    offset += 2
                    len
                }
                else -> break
            }
            if (offset+length>=data.size){break}
            val valueBytes=data.copyOfRange(offset,offset+length)
            val children =if (tag.isConstructed){
                parse(valueBytes)
            }
            else{
                emptyList()
            }
            nodes.add(TlvNode(tag,valueBytes,children))



        }
        return nodes

    }
}