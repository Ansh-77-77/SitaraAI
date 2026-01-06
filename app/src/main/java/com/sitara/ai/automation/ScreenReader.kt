package com.sitara.ai.automation

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityNodeInfo

object ScreenReader {

    fun read(service: AccessibilityService): String {
        val root = service.rootInActiveWindow ?: return ""
        val sb = StringBuilder()
        traverse(root, sb)
        return sb.toString().trim()
    }

    private fun traverse(node: AccessibilityNodeInfo?, sb: StringBuilder) {
        if (node == null) return

        node.text?.let {
            sb.append(it.toString()).append(". ")
        }

        for (i in 0 until node.childCount) {
            traverse(node.getChild(i), sb)
        }
    }
}
