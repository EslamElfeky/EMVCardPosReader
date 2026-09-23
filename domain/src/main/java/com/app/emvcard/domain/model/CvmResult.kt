package com.app.emvcard.domain.model

import java.security.Signature
import java.util.concurrent.locks.Condition

data class CvmResult(
    val method: CvmMethod,
    val successful: Boolean,
    val requiresSignature: Boolean=method.requiresSignature
){
    fun toEmvTag9F34(condition: Int): ByteArray{
        val b1=method.emvCode.toByte()
        val b2= condition.toByte()
        val b3= (if (successful)0x02 else 0x01).toByte()
        return byteArrayOf(b1,b2,b3)

    }
}
