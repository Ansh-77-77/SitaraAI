package com.sitara.ai.voice

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class SpeechManager(private val context: Context) {

    private val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private var finalText = ""
    private var onDone: ((String) -> Unit)? = null

    private val silenceHandler = Handler(Looper.getMainLooper())
    private val silenceTimeout = 1200L // 1.2 sec silence = done

    fun start(callback: (String) -> Unit) {
        finalText = ""
        onDone = callback

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-IN")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        recognizer.setRecognitionListener(object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {
                // Reset silence timer whenever sound detected
                silenceHandler.removeCallbacksAndMessages(null)
            }

            override fun onPartialResults(partialResults: Bundle?) {
                silenceHandler.removeCallbacksAndMessages(null)
                silenceHandler.postDelayed({
                    stop()
                }, silenceTimeout)
            }

            override fun onResults(results: Bundle?) {
                val list = results
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!list.isNullOrEmpty()) {
                    finalText += " " + list[0]
                }
                stop()
            }

            override fun onError(error: Int) {
                stop()
            }

            override fun onEndOfSpeech() {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        recognizer.startListening(intent)
    }

    fun manualDone() {
        stop()
    }

    private fun stop() {
        try {
            recognizer.stopListening()
        } catch (_: Exception) {}

        silenceHandler.removeCallbacksAndMessages(null)

        val text = finalText.trim()
        if (text.isNotEmpty()) {
            onDone?.invoke(text)
        }

        onDone = null
        finalText = ""
    }

    fun destroy() {
        recognizer.destroy()
    }
}
