package com.tinatang0424.app_project.firebase_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()
    }

    private fun register() {
        register_btn.setOnClickListener {
            if(TextUtils.isEmpty(email_edt.text.toString())) {
                Toast.makeText(this@RegisterActivity, "請輸入email", Toast.LENGTH_SHORT).show()
            }
            else if(TextUtils.isEmpty(password_edt.text.toString())) {
                Toast.makeText(this@RegisterActivity, "請輸入密碼", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(
                    email_edt.text.toString(),
                    password_edt.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val currentUser = auth.currentUser
                            val currentUserDb = databaseReference?.child((currentUser?.uid!!))

                            currentUserDb?.child("email")?.setValue(email_edt.text.toString())
                            currentUserDb?.child("password")?.setValue(password_edt.text.toString())

                            Toast.makeText(this@RegisterActivity, "註冊成功!", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "註冊失敗，請再試一次!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
        }
    }
}