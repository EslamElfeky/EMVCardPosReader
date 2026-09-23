package com.app.emvcard.domain.model

enum class CvmMethod(val emvCode:Int) {
    FAIL_CVM(0x00),
    OFFLINE_PLAIN_PIN(0x01),
    ONLINE_PIN(0x02),
    OFFLINE_PLAIN_PIN_AND_SIGNATURE(0x03),
    OFFLINE_ENCIPHERED_PIN(0x04),
    OFFLINE_ENCIPHERED_PIN_AND_SIGNATURE(0x05),
    SIGNATURE(0x1E),
    NO_CVM_REQUIRED(0x1F),
    CDCVM(0x21),
    UNKNOWN(0xFF);

    val requiresSignature: Boolean
        get()=this==SIGNATURE ||
               this==OFFLINE_PLAIN_PIN_AND_SIGNATURE||
               this==OFFLINE_ENCIPHERED_PIN_AND_SIGNATURE

    val isPin: Boolean
        get()=this in setOf(
            OFFLINE_PLAIN_PIN,
            OFFLINE_ENCIPHERED_PIN,
            ONLINE_PIN,
            OFFLINE_PLAIN_PIN_AND_SIGNATURE,
            OFFLINE_ENCIPHERED_PIN_AND_SIGNATURE
        )

    companion object{
        fun fromEmvCode(rowByte:Byte): CvmMethod{
            val code =rowByte.toInt() and 0x3F // Bits 6-1
            return entries.firstOrNull { it.emvCode==code }?: UNKNOWN
        }
    }
}