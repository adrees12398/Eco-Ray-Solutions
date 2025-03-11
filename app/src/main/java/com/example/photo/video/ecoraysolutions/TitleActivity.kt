package com.example.photo.video.ecoraysolutions

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.photo.video.ecoraysolutions.databinding.ActivityTitleBinding

class TitleActivity : AppCompatActivity() {
    lateinit var binding: ActivityTitleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTitleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()

    }

    private fun listener() {
        binding.userlogin.setOnClickListener {
            startActivity(Intent(this,PasswordActivity::class.java))
            finish()
        }
        binding.registrationbtn.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        }
    }
}