package com.sitara.ai.automation

import android.content.Context
import android.content.Intent
import android.net.Uri

object WhatsAppAutomation {

    fun send(context: Context, phone: String, message: String) {
        val uri = Uri.parse("smsto:$phone")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.setPackage("com.whatsapp")
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
