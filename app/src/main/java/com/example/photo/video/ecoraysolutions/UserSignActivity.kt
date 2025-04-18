package com.example.photo.video.ecoraysolutions

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.ecoraysolutions.databinding.ActivityUserSignBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class UserSignActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserSignBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private val RC_SIGN_IN = 100
    private var otpText = ""
    private val sessionManager by lazy { SessionManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Get from Firebase
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.SignUp.setOnClickListener {
            login()
        }

        binding.google.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!.idToken!!)
            } catch (e: ApiException) {
                showToast("Google Sign-In Failed: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        // Redirect to HomePage when user successfully signs in
                        val intent = Intent(this, HomePage::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                } else {
                    showToast("Sign-In Failed")
                }
            }
    }

    private fun login() {
        val email = binding.emaillogin.text.toString().trim()
        val pass = binding.etPass.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            showToast("Email is required")
            return
        }

        if (TextUtils.isEmpty(pass)) {
            showToast("Pin is required")
            return
        }

        if (pass == sessionManager.getPin()) {
            firebaseAuth.signInWithEmailAndPassword(email, "123456")
                .addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        showToast("Login Successfully")

                        // Save login session
                        sessionManager.setLogin(true)

                        // Navigate to Home Page
                        startActivity(Intent(this, HomePage::class.java))
                        finish()
                    } else {
                        showToast("Login failed, please check credentials")
                    }
                }
                .addOnFailureListener { e ->
                    showToast("Error: ${e.message}")
                }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
