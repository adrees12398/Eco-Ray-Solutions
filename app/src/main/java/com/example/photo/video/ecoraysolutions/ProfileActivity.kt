package com.example.photo.video.ecoraysolutions

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.ecoraysolutions.databinding.ActivityProfileBinding
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var firestore: FirebaseFirestore
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        sessionManager = SessionManager(this)
        setUpofsettext()
        binding.EditProfile.setOnClickListener {
            setUpofUpdate()
        }

    }

    private fun setUpofUpdate() {
        val name = binding.name.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val phone = binding.numberText.text.toString().trim()
        val pin = binding.Password.text.toString().trim()

        if (sessionManager.getUserId()?.isNotEmpty() == true) {
            sessionManager.getUserId().let {
                if (it != null) {
                    firestore.collection("Users").document(it)
                        .update("name", name, "email", email, "phoneNo", phone, "password", pin)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "UpDate Data Successfully", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Update Unsuccessfully:${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                }


            }


        }
    }

    private fun setUpofsettext() {

        if (sessionManager.getUserId()?.isNotEmpty() == true) {
            sessionManager.getUserId().let {
                if (it != null) {
                    firestore.collection("Users").document(it).get()
                        .addOnCompleteListener { task ->
                            val document = task.result
                            if (document.exists()) {
                                val name = document.getString("name") ?: ""
                                val email = document.getString("email") ?: ""
                                val phone = document.getString("phoneNo") ?: ""
                                val password = document.getString("password") ?: ""
                                runOnUiThread {
                                    binding.name.setText(name)
                                    binding.email.setText(email)
                                    binding.numberText.setText(phone)
                                    binding.Password.setText(password)
                                }

                            } else {
                                Log.i("TAG", "Error ", task.exception)
                            }
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Error:${e.message}", Toast.LENGTH_SHORT).show()

                        }
                }

            }

        }

    }
}