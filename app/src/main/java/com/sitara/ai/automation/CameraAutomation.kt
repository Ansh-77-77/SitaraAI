package com.sitara.ai.automation

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

object CameraAutomation {

    const val CAMERA_REQUEST = 101

    fun openCamera(activity: Activity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, CAMERA_REQUEST)
    }

    fun openGallery(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivity(intent)
    }
}
