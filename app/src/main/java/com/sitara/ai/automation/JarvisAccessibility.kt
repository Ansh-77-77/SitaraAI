package com.sitara.ai.automation

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class JarvisAccessibility : AccessibilityService() {

    companion object {
        var instance: JarvisAccessibility? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 🔒 SAFE MODE: do nothing
        // Automation baad me enable karenge
    }

    override fun onInterrupt() {}

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
