package com.example.drinking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

//        checkUser()
        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)

    }

    //session
//    private fun checkUser() {
//        //if user is already logged in go to main activity
//        //get current user
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            //user is already logged in
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        } else {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
//    }
}