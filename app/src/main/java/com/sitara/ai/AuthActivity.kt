package com.sitara.ai

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sitara.ai.automation.MemoryStore
import com.sitara.ai.automation.UserStore
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        UserStore.init(this)

        btnSignup.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isEmpty()) return@setOnClickListener

            MemoryStore.save(this, "user_name", name)
            MemoryStore.save(this, "user_title", "Sir")

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
