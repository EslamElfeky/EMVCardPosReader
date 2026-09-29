package com.app.emvcard.domain.emv

data class TlvNode(
    val tag: TlvTag,
    val value: ByteArray,
    val children: List<TlvNode> = emptyList()
) {

    val isConstructed : Boolean get() = tag.isConstructed
    fun findTag(tagHex: String): TlvNode?{
        if (tag.hexString.equals(tagHex,ignoreCase = true))
            return this
        for (child in children){
          val found=child.findTag(tagHex)
          if (found!=null){ return found}

        }
        return null
    }
    fun findTagValue(tagHex: String): ByteArray?=findTag(tagHex)?.value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TlvNode) return false

        return tag==other.tag &&
                value.contentEquals(other.value) &&
                children == other.children
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + value.contentHashCode()
        result = 31 * result + children.hashCode()
        return result
    }
}
