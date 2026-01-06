package com.sitara.ai.automation

import android.content.Context
import java.util.UUID

object UserStore {

    private const val PREF = "sitara_user"

    fun init(context: Context) {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        if (!sp.contains("credits")) {
            sp.edit()
                .putInt("credits", 50)
                .putString("referral", generateCode())
                .apply()
        }
    }

    fun applyPromo(context: Context, code: String): Boolean {
        if (code == "maizypherhun") {
            context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit().putBoolean("unlimited", true).apply()
            return true
        }
        return false
    }

    fun useReferral(context: Context, ref: String) {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val credits = sp.getInt("credits", 0)
        sp.edit().putInt("credits", credits + 25).apply()
    }

    fun addCredits(context: Context, c: Int) {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        sp.edit().putInt("credits", sp.getInt("credits", 0) + c).apply()
    }

    fun referralCode(context: Context): String =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
            .getString("referral", "")!!

    private fun generateCode(): String =
        UUID.randomUUID().toString().substring(0, 8)
}
