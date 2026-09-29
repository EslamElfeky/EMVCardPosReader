package com.app.emvcard.domain.emv.tlv

import com.app.emvcard.domain.emv.TlvNode
import com.app.emvcard.domain.emv.toHexString

// 1. Entry point for a single TlvNode
fun TlvNode.toPrettyTreeString(): String {
    val sb = StringBuilder()
    renderTree(this, sb, indent = "", isLast = true)
    return sb.toString()
}

// 2. Entry point for a List<TlvNode>
fun List<TlvNode>.toPrettyTreeString(): String {
    val sb = StringBuilder()
    forEachIndexed { index, node ->
        renderTree(node, sb, indent = "", isLast = index == lastIndex)
    }
    return sb.toString()
}

// 3. Private recursive rendering worker function (No extension receiver conflicts)
private fun renderTree(
    node: TlvNode,
    sb: StringBuilder,
    indent: String,
    isLast: Boolean
) {
    val branch = if (indent.isEmpty()) "" else if (isLast) "└── " else "├── "
    val childIndent = if (indent.isEmpty()) "" else if (isLast) "$indent    " else "$indent│   "
    val tagName = TlvDictionary.getName(node.tag.hexString)

    sb.append(indent)
    sb.append(branch)
    sb.append("[${node.tag.hexString}] $tagName")

    if (node.isConstructed) {
        sb.appendLine(" (Constructed, ${node.children.size} items)")
        node.children.forEachIndexed { index, child ->
            renderTree(child, sb, childIndent, index == node.children.lastIndex)
        }
    } else {
        val hexValue = node.value.toHexString()
        val ascii = getPrintableAscii(node.value)
        val valueSuffix = if (ascii.isNotEmpty()) " -> \"$ascii\"" else ""
        sb.appendLine(" (${node.value.size} bytes): $hexValue$valueSuffix")
    }
}

private fun getPrintableAscii(bytes: ByteArray): String {
    if (bytes.isEmpty()) return ""
    val isAsciiPrintable = bytes.all { it in 32..126 }
    return if (isAsciiPrintable) String(bytes, Charsets.US_ASCII) else ""
}