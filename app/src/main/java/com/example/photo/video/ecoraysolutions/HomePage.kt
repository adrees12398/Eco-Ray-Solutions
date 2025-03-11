package com.example.photo.video.ecoraysolutions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.ecoraysolutions.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {
    lateinit var binding: ActivityHomePageBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        sessionManager = SessionManager(this)
        binding.logout.setOnClickListener {
            sessionManager.setLogin(false)
            sessionManager.setFirstTime(false)
            firebaseAuth.signOut()
            val intent = Intent(this, RegistrationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            finish()

        }
    }
}