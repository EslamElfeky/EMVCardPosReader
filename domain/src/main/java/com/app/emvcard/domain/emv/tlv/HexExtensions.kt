package com.app.emvcard.domain.emv

fun ByteArray.toHexString(): String=joinToString("") {"%02x".format(it)  }

fun String.hexToBytes(): ByteArray{
    val clean=replace(" ","").replace(":","")
    require(clean.length %2 ==0){ "Hex string must have an even length" }
    return ByteArray(clean.length/2){ index->
        clean.substring(index*2,index*2+2).toInt(16).toByte()

    }
}