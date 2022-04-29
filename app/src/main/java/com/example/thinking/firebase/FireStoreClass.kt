package com.example.thinking.firebase

import android.app.Activity
import com.example.thinking.activities.MainActivity
import com.example.thinking.activities.MyProfile
import com.example.thinking.activities.SignInActivity
import com.example.thinking.activities.SignUpActivity
import com.example.thinking.models.User
import com.example.thinking.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {
    private val mFirestore =  FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User) {
        // creating a collection with name in Firestore DB
        mFirestore.collection(Constants.USERS)
             // creates a collection with the unique id as name
            .document(getCurrentUserId())
             // adds all the data in the user model
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
    }

    fun loadUserData(activity: Activity) {
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)
                // checking which activity...
                when (activity) {
                    is SignInActivity -> {
                        if (loggedInUser != null) {
                            activity.signInSuccess(loggedInUser)
                        }
                    }
                    is MainActivity -> {
                        if (loggedInUser != null) {
                            activity.updateNavigationUserDetails(loggedInUser)
                        }
                    }
                    is MyProfile -> {
                        if (loggedInUser != null) {
                            activity.setUserDataInUI(loggedInUser)
                        }
                    }
                }
            }
    }

    fun getCurrentUserId(): String {
        // currentUser has everything of the user
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }
}