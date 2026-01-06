package com.sitara.ai.network

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object AiClient {

    private val client = OkHttpClient()

    // 🔒 SYSTEM PROMPT (VERY IMPORTANT)
    private const val SYSTEM_PROMPT = """
You are Sitara AI, an Android automation assistant.

RULES:
- If user asks for an action, reply ONLY in pure JSON.
- Do NOT add explanation text.
- Do NOT wrap JSON in markdown.
- If no automation needed, reply with normal human text.

SUPPORTED INTENTS:

OPEN_APP:
{
  "intent": "OPEN_APP",
  "package": "com.whatsapp"
}

SEND_WHATSAPP:
{
  "intent": "SEND_WHATSAPP",
  "phone": "+919XXXXXXXXX",
  "message": "text message"
}

FLASHLIGHT_ON:
{
  "intent": "FLASHLIGHT_ON"
}

FLASHLIGHT_OFF:
{
  "intent": "FLASHLIGHT_OFF"
}

VOLUME_UP:
{
  "intent": "VOLUME_UP"
}

VOLUME_DOWN:
{
  "intent": "VOLUME_DOWN"
}
"""

    fun ask(text: String, callback: (String) -> Unit) {

        val body = JSONObject().apply {
            put(
                "messages",
                listOf(
                    mapOf("role" to "system", "content" to SYSTEM_PROMPT),
                    mapOf("role" to "user", "content" to text)
                )
            )
            put("model", "llama-3.3-70b-versatile")
        }

        val request = Request.Builder()
            .url("http://YOUR_SERVER_IP:3000/api/ai/command")
            .post(
                RequestBody.create(
                    MediaType.parse("application/json"),
                    body.toString()
                )
            )
            .addHeader("x-api-key", "super-secret-key-123")
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback("Network error")
            }

            override fun onResponse(call: Call, response: Response) {
                val reply = response.body()?.string() ?: ""
                callback(reply)
            }
        })
    }
}
