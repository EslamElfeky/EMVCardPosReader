package com.app.emvcard.domain.model

data class CardPresentation(
    val technology: CardTechnology,
    val atr: ByteArray=byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CardPresentation

        if (technology != other.technology) return false
        if (!atr.contentEquals(other.atr)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = technology.hashCode()
        result = 31 * result + atr.contentHashCode()
        return result
    }
}
