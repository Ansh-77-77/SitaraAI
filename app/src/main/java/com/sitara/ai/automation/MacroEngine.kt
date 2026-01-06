package com.sitara.ai.automation

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper

data class MacroStep(
    val action: String,
    val value: String? = null
)

object MacroEngine {

    private val handler = Handler(Looper.getMainLooper())

    fun run(
        service: AccessibilityService,
        steps: List<MacroStep>
    ) {
        var delay = 0L
        steps.forEach { step ->
            handler.postDelayed({
                when (step.action) {
                    "CLICK_ID" ->
                        AccessibilityActions.clickById(service, step.value!!)

                    "CLICK_TEXT" ->
                        AccessibilityActions.clickByText(service, step.value!!)

                    "TYPE" ->
                        AccessibilityActions.typeText(service, step.value!!.split("|")[0],
                            step.value.split("|")[1])

                    "BACK" ->
                        AccessibilityActions.globalBack(service)

                    "HOME" ->
                        AccessibilityActions.globalHome(service)
                }
            }, delay)
            delay += 900 // gap between steps
        }
    }
}
