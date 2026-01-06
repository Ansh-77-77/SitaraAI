package com.sitara.ai.automation

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object MacroStore {

    private const val PREF = "sitara_macros"

    fun save(context: Context, name: String, steps: JSONArray) {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        sp.edit().putString(name, steps.toString()).apply()
    }

    fun load(context: Context, name: String): JSONArray? {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val raw = sp.getString(name, null) ?: return null
        return JSONArray(raw)
    }

    fun all(context: Context): List<String> {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return sp.all.keys.toList()
    }

    // 🔹 Export all macros as JSON
    fun exportAll(context: Context): JSONObject {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val out = JSONObject()
        sp.all.forEach { (k, v) ->
            out.put(k, JSONArray(v as String))
        }
        return out
    }

    // 🔹 Import macros from JSON
    fun importAll(context: Context, data: JSONObject) {
        val sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val ed = sp.edit()
        data.keys().forEach { key ->
            ed.putString(key, data.getJSONArray(key).toString())
        }
        ed.apply()
    }
}
