package com.sitara.ai.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class TTSManager(context: Context) : TextToSpeech(context, null) {

    init {
        language = Locale("en", "IN")
        setPitch(0.9f)
        setSpeechRate(0.95f)
    }

    fun speak(text: String) {
        speak(text, QUEUE_FLUSH, null, null)
    }
}
