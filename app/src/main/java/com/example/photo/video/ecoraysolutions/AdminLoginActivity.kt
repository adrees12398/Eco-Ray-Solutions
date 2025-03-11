package com.example.photo.video.ecoraysolutions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.ecoraysolutions.databinding.ActivityAdminLoginBinding

class AdminLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.arrow.setOnClickListener {
            this.finish()
        }
        binding.SignUp.setOnClickListener {
            val email = binding.emaillogin.text.toString()
            val password = binding.password.text.toString()

            if (email == "admin@gmail.com" && password == "12345") {
                startActivity(Intent(this, AdminActivity::class.java))
               this.finish()
            }
        }

    }

}