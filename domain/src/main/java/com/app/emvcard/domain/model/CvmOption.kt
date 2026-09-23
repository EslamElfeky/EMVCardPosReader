package com.app.emvcard.domain.model

data class CvmOption(val method: CvmMethod,
    val condition: Int,
    val applyNextOnFailure: Boolean=false
)
{
    companion object{
        fun parse(byte1: Byte,byte2: Byte): CvmOption{
            val applyNext=(byte1.toInt() and 0x40)!=0
            val method= CvmMethod.fromEmvCode(byte1)
            val condition=byte2.toInt() and 0xFF
            return CvmOption(
                method = method,
                condition=condition,
                applyNextOnFailure = applyNext
            )
        }
    }
}