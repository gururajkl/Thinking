package com.example.thinking.activities

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.content.ContextCompat
import com.example.thinking.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dialog_progress.*

open class BaseActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    // initializing the loading animation here.
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showProgressDialog(text: String) {
        // setting the var to dialog of this activity
        mProgressDialog = Dialog(this)

        // setting the content
        mProgressDialog.setContentView(R.layout.dialog_progress)

        // changing the text from the attribute of the fn
        mProgressDialog.tv_progress_text.text = text

        // showing the dialog
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun getCurrentUserID(): String {
        // return the userID of the user as a string.
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press again to exit!", Toast.LENGTH_LONG).show()

        // if user did not pressed back for a while, reset this method
        Handler().postDelayed({doubleBackToExitPressedOnce = false}, 2000)
    }


    fun showErrorSnaKBar(message: String) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.error_color))
        snackBar.show()
    }
}