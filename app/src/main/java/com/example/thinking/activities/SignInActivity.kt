package com.example.thinking.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.thinking.R
import com.example.thinking.databinding.ActivitySignInBinding
import com.example.thinking.firebase.FireStoreClass
import com.example.thinking.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {
    private var binding: ActivitySignInBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()

        btn_sign_in.setOnClickListener {
            signInUser()
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding?.toolbarSignUp)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        // goes back
        binding?.toolbarSignUp?.setNavigationOnClickListener { onBackPressed() }
    }

    fun signInSuccess(user: User) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        hideProgressDialog()
    }

    private fun signInUser() {
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString()

        if (validateForm(email, password)) {
            // Refer SignUp Activity
            showProgressDialog("Please wait...")
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                hideProgressDialog()
                if (task.isSuccessful) {
                    FireStoreClass().loadUserData(this)
                    Toast.makeText(this, "SignIn success", Toast.LENGTH_LONG).show()
                } else {
                    Log.e("Sign in", "SignInWithEmail: Failure", task.exception)
                    showErrorSnaKBar(task.exception!!.message!!)
                }
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        if (email.isEmpty() or password.isEmpty()) {
            showErrorSnaKBar("All fields are mandatory!")
            return false
        }
        return true
    }
}