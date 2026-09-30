package com.app.emvcard.domain.emv.tlv

class TlvMap(
    val elements: LinkedHashMap<String, ByteArray> = LinkedHashMap(),
    val duplicates: MutableMap<String, MutableList<ByteArray>> = LinkedHashMap()
) {

    fun findTagValue(tagHex: String): ByteArray? = elements[tagHex.uppercase()]

    fun findScopedTagValue(parentTagHex: String, tagHex: String): ByteArray? {
        val path = "${parentTagHex.uppercase()}/${tagHex.uppercase()}"
        return elements[path] ?: elements[tagHex.uppercase()]
    }

    fun findAllTagValues(tagHex: String): List<ByteArray> {
        return duplicates[tagHex.uppercase()]
            ?: elements[tagHex.uppercase()]?.let { listOf(it) }
            ?: emptyList()
    }

    fun containsTag(tagHex: String): Boolean = elements.containsKey(tagHex.uppercase())

    fun put(tagHex: String, value: ByteArray, parentTagHex: String? = null) {
        val upperTag = tagHex.uppercase()
        if (elements.containsKey(upperTag)) {
            val list = duplicates.getOrPut(upperTag) {
                mutableListOf(elements[upperTag]!!)
            }
            list.add(value)

            if (!parentTagHex.isNullOrBlank()) {
                elements["${parentTagHex.uppercase()}/$upperTag"] = value
            }
        } else {
            elements[upperTag] = value
        }
    }
}