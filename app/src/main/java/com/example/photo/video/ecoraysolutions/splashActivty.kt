package com.example.photo.video.ecoraysolutions

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.ecoraysolutions.Models.SignUpModel
import com.example.photo.video.ecoraysolutions.databinding.ActivitySplashActivtyBinding
import com.google.firebase.firestore.FirebaseFirestore

class splashActivty : AppCompatActivity() {
    lateinit var bindingsplashActivty: ActivitySplashActivtyBinding

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    val sessionManager by lazy {
        SessionManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingsplashActivty = ActivitySplashActivtyBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash_activty)
        if (sessionManager.getLogin()) {
            splash()
        } else {
            isApprove()
        }
    }

    private fun isApprove() {
        if (sessionManager.getUserId()?.isNotEmpty() == true) {
            sessionManager.getUserId()?.let {
                firestore.collection("Users").document(it).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val model = task.result.toObject(SignUpModel::class.java)
                        if (model?.accountStatus == true) {
                            sessionManager.setLogin(true)
                            splash()
                        }
                    }
                }.addOnFailureListener { error ->
                    splash()
                    Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            splash()
        }
    }

    private fun splash() {
        if (sessionManager.getLogin()) {
            if (sessionManager.getFirstTime()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, HomePage::class.java))
                    finish()
                },2000)
            } else {
                startActivity(Intent(this, PasswordActivity::class.java))
                finish()
            }
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, TitleActivity::class.java))
                finish()
            }, 2000)
        }
    }
}