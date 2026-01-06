package com.sitara.ai.automation

object MacroLibrary {

    fun get(name: String): List<MacroStep>? {
        return when (name) {

            "YOUTUBE_PLAY" -> listOf(
                MacroStep("CLICK_TEXT", "YouTube"),
                MacroStep("CLICK_ID", "com.google.android.youtube:id/search"),
                MacroStep(
                    "TYPE",
                    "com.google.android.youtube:id/search_edit_text|${'$'}QUERY"
                ),
                MacroStep("CLICK_TEXT", "${'$'}QUERY")
            )

            "CHROME_SEARCH" -> listOf(
                MacroStep("CLICK_TEXT", "Chrome"),
                MacroStep(
                    "TYPE",
                    "com.android.chrome:id/url_bar|${'$'}QUERY"
                )
            )

            "OPEN_WIFI_SETTINGS" -> listOf(
                MacroStep("CLICK_TEXT", "Settings"),
                MacroStep("CLICK_TEXT", "Wi-Fi")
            )

            "OPEN_BLUETOOTH_SETTINGS" -> listOf(
                MacroStep("CLICK_TEXT", "Settings"),
                MacroStep("CLICK_TEXT", "Bluetooth")
            )

            "GO_HOME" -> listOf(
                MacroStep("HOME")
            )

            "GO_BACK" -> listOf(
                MacroStep("BACK")
            )

            else -> null
        }
    }
}
