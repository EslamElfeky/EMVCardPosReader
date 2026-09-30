package com.app.emvcard.domain.emv.tlv

import com.app.emvcard.domain.emv.toHexString

fun TlvNode.toPrettyTreeString(): String {
    val sb = StringBuilder()
    renderSingleNode(this, sb)
    return sb.toString()
}

fun List<TlvNode>.toPrettyTreeString(): String {
    val sb = StringBuilder()
    forEachIndexed { index, node ->
        renderFlatNode(node, sb, isLast = index == lastIndex)
    }
    return sb.toString()
}

fun TlvMap.toPrettyTreeString(): String {
    val sb = StringBuilder()
    val entries = elements.entries.toList()
    entries.forEachIndexed { index, (key, value) ->
        val isScoped = key.contains("/")
        val parentPath = if (isScoped) key.substringBeforeLast("/") else null
        val tagHex = if (isScoped) key.substringAfterLast("/") else key
        renderElement(tagHex, value, parentPath, sb, isLast = index == entries.lastIndex)
    }
    return sb.toString()
}

private fun renderSingleNode(node: TlvNode, sb: StringBuilder) {
    val tagName = TlvDictionary.getName(node.tag.hexString)
    val hexValue = node.value.toHexString()
    val ascii = getPrintableAscii(node.value)
    val valueSuffix = if (ascii.isNotEmpty()) " -> \"$ascii\"" else ""
    val parentPrefix = if (!node.parentTagHex.isNullOrBlank()) "[${node.parentTagHex}] / " else ""

    sb.appendLine("$parentPrefix[${node.tag.hexString}] $tagName (${node.value.size} bytes): $hexValue$valueSuffix")
}

private fun renderFlatNode(node: TlvNode, sb: StringBuilder, isLast: Boolean) {
    val branch = if (isLast) "└── " else "├── "
    val tagName = TlvDictionary.getName(node.tag.hexString)
    val hexValue = node.value.toHexString()
    val ascii = getPrintableAscii(node.value)
    val valueSuffix = if (ascii.isNotEmpty()) " -> \"$ascii\"" else ""
    val scopeIndicator = if (!node.parentTagHex.isNullOrBlank()) "<${node.parentTagHex}> " else ""

    sb.append(branch)
    sb.appendLine("$scopeIndicator[${node.tag.hexString}] $tagName (${node.value.size} bytes): $hexValue$valueSuffix")
}

private fun renderElement(
    tagHex: String,
    value: ByteArray,
    parentPath: String?,
    sb: StringBuilder,
    isLast: Boolean
) {
    val branch = if (isLast) "└── " else "├── "
    val tagName = TlvDictionary.getName(tagHex)
    val hexValue = value.toHexString()
    val ascii = getPrintableAscii(value)
    val valueSuffix = if (ascii.isNotEmpty()) " -> \"$ascii\"" else ""
    val scopePrefix = if (parentPath != null) "$parentPath/" else ""

    sb.append(branch)
    sb.appendLine("[$scopePrefix$tagHex] $tagName (${value.size} bytes): $hexValue$valueSuffix")
}

private fun getPrintableAscii(bytes: ByteArray): String {
    if (bytes.isEmpty()) return ""
    val isAsciiPrintable = bytes.all { it in 32..126 }
    return if (isAsciiPrintable) String(bytes, Charsets.US_ASCII) else ""
}