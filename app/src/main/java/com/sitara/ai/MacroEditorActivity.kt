package com.sitara.ai

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sitara.ai.automation.*
import kotlinx.android.synthetic.main.activity_macro_editor.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MacroEditorActivity : AppCompatActivity() {

    private val IMPORT_REQ = 201
    private val EXPORT_REQ = 202

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_macro_editor)

        btnSave.setOnClickListener {
            val name = macroName.text.toString().trim()
            val rawSteps = macroSteps.text.toString().trim()
            if (name.isEmpty() || rawSteps.isEmpty()) return@setOnClickListener

            val arr = JSONArray()
            rawSteps.lines().forEach { line ->
                val p = line.split(" ", limit = 2)
                val o = JSONObject().put("action", p[0])
                if (p.size > 1) o.put("value", p[1])
                arr.put(o)
            }
            MacroStore.save(this, name, arr)
            status.text = "Macro saved"
        }

        btnRun.setOnClickListener {
            val name = macroName.text.toString().trim()
            val arr = MacroStore.load(this, name) ?: return@setOnClickListener
            val steps = mutableListOf<MacroStep>()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                steps.add(MacroStep(o.getString("action"), o.optString("value", null)))
            }
            JarvisAccessibility.instance?.let { MacroEngine.run(it, steps) }
            status.text = "Macro running"
        }

        // 📤 Export
        btnExport.setOnClickListener {
            val data = MacroStore.exportAll(this)
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                type = "application/json"
                putExtra(Intent.EXTRA_TITLE, "sitara_macros.json")
            }
            startActivityForResult(intent, EXPORT_REQ)
            cache = data.toString()
        }

        // 📥 Import
        btnImport.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "application/json"
            }
            startActivityForResult(intent, IMPORT_REQ)
        }
    }

    private var cache: String = ""

    override fun onActivityResult(req: Int, res: Int, data: Intent?) {
        super.onActivityResult(req, res, data)
        if (res != RESULT_OK || data?.data == null) return

        when (req) {
            EXPORT_REQ -> writeFile(data.data!!, cache)
            IMPORT_REQ -> readFile(data.data!!)
        }
    }

    private fun writeFile(uri: Uri, text: String) {
        contentResolver.openOutputStream(uri)?.use {
            it.write(text.toByteArray())
        }
        status.text = "Exported"
    }

    private fun readFile(uri: Uri) {
        val br = BufferedReader(InputStreamReader(contentResolver.openInputStream(uri)))
        val json = JSONObject(br.readText())
        MacroStore.importAll(this, json)
        status.text = "Imported"
    }
}
