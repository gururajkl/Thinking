package com.example.thinking.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.thinking.R
import com.example.thinking.firebase.FireStoreClass
import com.example.thinking.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.util.jar.Manifest

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener { // to make items on menu selectable

    companion object {
        const val MY_PROFILE_REQUEST_CODE: Int = 11
        const val CREATE_BOARD_REQUEST_CODE: Int = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpActionBar()
        // setting up the nav_view
        nav_view.setNavigationItemSelectedListener(this)

        // calling signInUser
        FireStoreClass().loadUserData(this)
    }

    fun updateNavigationUserDetails(user: User) {
        // 3rd party lib for getting or downloading the image from the DB
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(nav_user_image)

        tv_username.text = user.name
        mainPageUpdateUI(user)
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar_main_activity)
        title = "Thinking"
        toolbar_main_activity.setNavigationIcon(R.drawable.ic_baseline_menu_24)
        toolbar_main_activity.setNavigationOnClickListener { toggleDrawer() }
    }

    private fun toggleDrawer() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            doubleBackToExit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                startActivity(Intent(this, MyProfile::class.java))
            }
            R.id.nav_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }
            else -> {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }

    private fun mainPageUpdateUI(user: User) {
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(iv__user_image_main)

        tv_USN_main.text = user.usn.uppercase()
        tv_email_main.text = user.email
        tv_name_main.text = user.name
    }
}