package com.sitara.ai.automation

import android.content.Context
import android.content.Intent

object AppLauncher {

    fun open(context: Context, packageName: String) {
        val intent = context.packageManager
            .getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
