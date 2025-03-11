package com.example.photo.video.ecoraysolutions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.ecoraysolutions.Models.SignUpModel
import com.example.photo.video.ecoraysolutions.databinding.ActivityPasswordBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.otpview.OTPListener

class PasswordActivity : AppCompatActivity() {

    private var otpText = ""
    lateinit var binding: ActivityPasswordBinding
    lateinit var sessionManager: SessionManager
    private val firestore by lazy { FirebaseFirestore.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        binding.otpView.requestFocus()
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {}

            override fun onOTPComplete(otp: String) {
                otpText = otp
            }
        }

        binding.loginpin.setOnClickListener {
            if (sessionManager.getLogin()) {
                if (otpText.isNotEmpty()) {
                    if (sessionManager.getPin().equals(otpText)) {
                        sessionManager.setFirstTime(true)
                        startActivity(Intent(this, HomePage::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Password is Required", Toast.LENGTH_SHORT).show()
                }
            } else {
                isApprove()
            }
        }
    }

    private fun isApprove() {
        if (sessionManager.getUserId()?.isNotEmpty() == true) {
            sessionManager.getUserId()?.let {
                firestore.collection("Users").document(it).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val model = task.result.toObject(SignUpModel::class.java)
                        if (model?.accountStatus == true) {
//                        login
                            startActivity(Intent(this, HomePage::class.java))
                        } else {
                            // Not approve
                            Toast.makeText(this, "Admin Not Approve Your Request", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Kindly Registration Your account", Toast.LENGTH_SHORT).show()
        }
    }
}