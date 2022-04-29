package com.example.thinking.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.thinking.R
import com.example.thinking.databinding.ActivitySignUpBinding
import com.example.thinking.firebase.FireStoreClass
import com.example.thinking.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    private var binding: ActivitySignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpActionBar()

        btn_sign_up.setOnClickListener {
            registerUser()
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

    // calling from the FireStoreClass
    fun userRegisteredSuccess() {
        Toast.makeText(this, "Your account created", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, SignInActivity::class.java))
        hideProgressDialog()
    }

    // registering the user to the FIREBASE
    private fun registerUser() {
        val name: String = et_name_signup.text.toString()
        val email: String = et_email_signup.text.toString().trim { it <= ' ' }
        val password: String = et_password_signup.text.toString()

        if (validateForm(name, email, password)) {
            // showing progress bar here
            showProgressDialog("Please wait...")
            // calling firebase auth method to create new user
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                hideProgressDialog()
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    // using user model to create
                    val user = User(firebaseUser.uid, name, registeredEmail)
                    // calling the class
                    FireStoreClass().registerUser(this, user)
                } else {
                    Log.e("Sign up", "SignUpWithEmail: Failure", task.exception)
                    showErrorSnaKBar(task.exception!!.message!!)
                }
            }
        }
    }

    // checks if user has entered or not in the fields
    private fun validateForm(name: String, email: String, password: String): Boolean {
        if (name.isEmpty() or email.isEmpty() or password.isEmpty()) {
            showErrorSnaKBar("All fields are mandatory!")
            return false
        }
        return true
    }
}