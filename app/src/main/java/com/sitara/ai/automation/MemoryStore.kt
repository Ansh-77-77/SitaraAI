package com.sitara.ai.automation

import android.content.Context

object MemoryStore {

    private const val PREF = "sitara_memory"

    fun save(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        sp.edit().putString(key, value).apply()
    }

    fun get(context: Context, key: String): String? {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return sp.getString(key, null)
    }

    fun getAll(context: Context): Map<String, *> {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return sp.all
    }
}
