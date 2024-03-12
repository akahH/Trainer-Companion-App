package com.example.trainercompanionapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trainercompanionapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="Sign Up"

        auth = Firebase.auth
        val signUp = findViewById<Button>(R.id.signUpButton)
        val user = findViewById<EditText>(R.id.logInUser)
        val password = findViewById<EditText>(R.id.logInPassword)
        signUp.setOnClickListener{
            if(TextUtils.isEmpty(user.text) || TextUtils.isEmpty(password.text))
                Toast.makeText(applicationContext, "Empty Credentials", Toast.LENGTH_SHORT).show()
            else if (password.text.length < 8)
                Toast.makeText(applicationContext, "Password is too small", Toast.LENGTH_SHORT).show()
            else
                registerUser( user.text.toString(), password.text.toString())
        }

    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(applicationContext, "Registation Complete", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            else
                Toast.makeText(applicationContext, "Registation Failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}