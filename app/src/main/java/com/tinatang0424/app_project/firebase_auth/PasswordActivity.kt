package com.tinatang0424.app_project.firebase_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.activity_register.*

class PasswordActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        changepw()

    }

    private fun changepw() {
        submit_btn.setOnClickListener {
            if(TextUtils.isEmpty(newpw_edt.text.toString())) {
                email_edt.setError("請輸入密碼")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(checkpw_edt.text.toString())) {
                password_edt.setError("請再輸入一次密碼")
                return@setOnClickListener
            }
            else {
                if(newpw_edt.text.toString()==checkpw_edt.text.toString()) {
                    //可以改密碼
                    val currentuser = auth.currentUser
                    val newPassword = newpw_edt.text.toString()
                    val currentUserDb = databaseReference?.child((currentuser?.uid!!))

                    currentuser?.updatePassword(newPassword)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                               //currentUserDb?.child("email")?.setValue(email_edt.text.toString())
                                currentUserDb?.child("password")?.setValue(newPassword)

                                Toast.makeText(this@PasswordActivity,"成功修改密碼!", Toast.LENGTH_LONG).show()

                                startActivity(Intent(this@PasswordActivity,MainActivity::class.java))
                                finish()
                            }
                        }
                }
                else {
                    Toast.makeText(this@PasswordActivity,"修改密碼失敗，請重新輸入驗證密碼!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}