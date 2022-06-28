package com.tinatang0424.app_project.firebase_auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        login()
        registerPage()

    }

    private fun login() {
        login_btn.setOnClickListener {
            if(TextUtils.isEmpty(email_edt.text.toString())) {
                Toast.makeText(this@LoginActivity, "請輸入email", Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(password_edt.text.toString())) {
                Toast.makeText(this@LoginActivity, "請輸入密碼", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.signInWithEmailAndPassword(
                    email_edt.text.toString(),
                    password_edt.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Log.w("error reason ",it.exception)
                            Toast.makeText(this@LoginActivity, "登入失敗，請再試一次!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun registerPage() {
        register_page_btn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }
}