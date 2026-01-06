package com.sitara.ai.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.sitara.ai.MainActivity
import com.sitara.ai.voice.SpeechManager

class WakeWordService : Service() {

    private lateinit var speechManager: SpeechManager
    private lateinit var wakeLock: PowerManager.WakeLock

    override fun onCreate() {
        super.onCreate()

        // Keep CPU awake (screen-off listening)
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "SitaraAI::WakeLock"
        )
        wakeLock.acquire()

        speechManager = SpeechManager(this)
        startForeground(1, createNotification())

        listenLoop()
    }

    private fun listenLoop() {
        speechManager.start { text ->
            if (text.lowercase().contains("hey sitara")) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            listenLoop()
        }
    }

    private fun createNotification(): Notification {
        val channelId = "sitara_bg"

        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                channelId,
                "Sitara Background",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Sitara AI")
            .setContentText("Listening for 'Hey Sitara'")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (wakeLock.isHeld) wakeLock.release()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
