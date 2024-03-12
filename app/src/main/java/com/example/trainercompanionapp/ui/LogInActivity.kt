package com.example.trainercompanionapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.trainercompanionapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    private lateinit var googleSignInClient : GoogleSignInClient

    companion object{
        private const val RC_SIGN_IN = 120
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title ="Log In"
        val googleLogIn = findViewById<Button>(R.id.logInGoogle)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("547103696346-hr2rjgm3gi6mqtcagh7md4spp33vds9k.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)

        googleLogIn.setOnClickListener {
            signIn()
        }

        auth = Firebase.auth
        val logIn = findViewById<Button>(R.id.logInButton)
        val user = findViewById<EditText>(R.id.logInUser)
        val password = findViewById<EditText>(R.id.logInPassword)
        logIn.setOnClickListener{
            if(TextUtils.isEmpty(user.text) || TextUtils.isEmpty(password.text))
                Toast.makeText(applicationContext, "Empty Credentials", Toast.LENGTH_SHORT).show()
            else if (password.text.length < 8)
                Toast.makeText(applicationContext, "Password is too small", Toast.LENGTH_SHORT).show()
            else
                logInUser( user.text.toString(), password.text.toString())
        }

        val signUp = findViewById<Button>(R.id.signUpButton)
        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(applicationContext, "Log in Successful", Toast.LENGTH_SHORT).show()
                finish()
            }

            else
                Toast.makeText(applicationContext, "Invalid Log In Credentials", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun signIn(){
        val signIntent = googleSignInClient.signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch ( e: ApiException){

            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken : String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Sign in", "signInWithCredential:success")
                    val user = auth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Sign in", "signInWithCredential:failure", task.exception)

                }
            }
    }
}