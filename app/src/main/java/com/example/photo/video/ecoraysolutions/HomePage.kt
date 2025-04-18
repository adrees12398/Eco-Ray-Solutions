package com.example.photo.video.ecoraysolutions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHost
import androidx.navigation.ui.setupWithNavController
import com.example.photo.video.ecoraysolutions.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {
    lateinit var binding: ActivityHomePageBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var sessionManager: SessionManager

    private val navController by lazy {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHost
        navHost.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bottomNavigation()
    }
   private fun bottomNavigation(){
    binding.nav.setupWithNavController(navController)
   }
}