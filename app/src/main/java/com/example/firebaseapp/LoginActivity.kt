package com.example.firebaseapp

import android.app.ProgressDialog
import android.content.Intent

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //global variables
    private var email: String? = null
    private var password: String? = null

    //UI elements
    private var tvForgotPassword: TextView? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Firebase references
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialise()
    }



    private fun initialise() {
        tvForgotPassword = findViewById<View>(R.id.textView) as TextView
        etEmail = findViewById<View>(R.id.editText5) as EditText
        etPassword = findViewById<View>(R.id.editText6) as EditText
        btnLogin = findViewById<View>(R.id.button2) as Button
        btnCreateAccount = findViewById<View>(R.id.button3) as Button
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!
            .setOnClickListener { startActivity(Intent(this@LoginActivity,
                ForgotPasswordActivity::class.java)
            ) }

        btnCreateAccount!!
            .setOnClickListener { startActivity(Intent(this@LoginActivity,
                CreatNewAccountActivity::class.java)) }

        btnLogin!!.setOnClickListener { loginUser() }
    }
    private fun loginUser() {

        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgressBar!!.setMessage("Registering User...")
            mProgressBar!!.show()

            Log.d(TAG, "Logging in user.")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->

                    mProgressBar!!.hide()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateUI() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
