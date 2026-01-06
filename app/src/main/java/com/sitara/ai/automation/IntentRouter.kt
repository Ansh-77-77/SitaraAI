package com.sitara.ai.automation

import android.app.Activity
import com.sitara.ai.voice.TTSManager
import org.json.JSONObject

object IntentRouter {

    fun handle(activity: Activity, json: String) {
        val obj = JSONObject(json)

        when (obj.getString("intent")) {

            "SCREEN_ANALYZE" -> {
                val svc = JarvisAccessibility.instance ?: return
                val screenText = svc.getScreenText()

                val tts = TTSManager(activity)
                if (screenText.isBlank()) {
                    tts.speak("Screen par koi readable text nahi mila")
                } else {
                    tts.speak("Screen par ye dikh raha hai. $screenText")
                }
            }

            "RUN_MACRO" -> {
                // existing macro logic
            }

            "MEMORY_SAVE" ->
                MemoryStore.save(
                    activity,
                    obj.getString("key"),
                    obj.getString("value")
                )
        }
    }
}
