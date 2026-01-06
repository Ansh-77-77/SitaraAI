package com.sitara.ai.automation

import android.content.Context
import android.hardware.camera2.CameraManager
import android.media.AudioManager

object SystemActions {

    fun flashlight(context: Context, on: Boolean) {
        val cam = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cam.setTorchMode(cam.cameraIdList[0], on)
    }

    fun volumeUp(context: Context) {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.adjustVolume(AudioManager.ADJUST_RAISE, 0)
    }

    fun volumeDown(context: Context) {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        am.adjustVolume(AudioManager.ADJUST_LOWER, 0)
    }
}
