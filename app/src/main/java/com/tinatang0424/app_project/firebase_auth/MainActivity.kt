package com.tinatang0424.app_project.firebase_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        //database = FirebaseDatabase.getInstance()
        //databaseReference = database?.reference!!.child("profile")

        loadProfile()

    }

    private fun loadProfile() {
        val user = auth.currentUser
        //val userreference = databaseReference?.child(user?.uid!!)


        title_text.text = "Hello, " + user?.email

        /*
        userreference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                title_text.text = snapshot.child("email").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        */

        logout_btn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()
        }

        change_pw_btn.setOnClickListener {
            startActivity(Intent(this@MainActivity,PasswordActivity::class.java))
            finish()
        }
    }

}