package com.sitara.ai

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sitara.ai.voice.SpeechManager
import com.sitara.ai.voice.TTSManager
import com.sitara.ai.network.AiClient
import com.sitara.ai.automation.IntentRouter
import com.sitara.ai.service.WakeWordService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var speechManager: SpeechManager
    private lateinit var ttsManager: TTSManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        speechManager = SpeechManager(this)
        ttsManager = TTSManager(this)

        btnSpeak.setOnClickListener {
            startVoiceFlow()
        }

        // 🔥 SAFE service start (NO CRASH)
        val intent = Intent(this, WakeWordService::class.java)
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun startVoiceFlow() {
        speechManager.start { text ->
            AiClient.ask(text) { reply ->
                runOnUiThread {
                    try {
                        IntentRouter.handle(this, reply)
                    } catch (e: Exception) {
                        ttsManager.speak(reply)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechManager.destroy()
        ttsManager.shutdown()
    }
}
