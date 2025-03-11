package com.example.photo.video.ecoraysolutions

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photo.video.ecoraysolutions.Adapters.AdminAdapter
import com.example.photo.video.ecoraysolutions.Models.SignUpModel
import com.example.photo.video.ecoraysolutions.databinding.ActivityAdminBinding
import com.google.firebase.firestore.FirebaseFirestore

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminBinding
    lateinit var firestore: FirebaseFirestore
    lateinit var adapter: AdminAdapter
    private var list = ArrayList<SignUpModel>()
    private val sessionManager: SessionManager by lazy { SessionManager(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        fetchUserData()
        recyclerViewSetup()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchUserData() {
        firestore.collection("Users").get()
            .addOnCompleteListener { loaduser ->
                if (loaduser.isSuccessful) {
                    list.clear()
                    val documents = loaduser.result?.documents
                    documents?.forEach { document ->
                        val user = document.toObject(SignUpModel::class.java).apply { this?.id = document.id }
                        user?.let { list.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                } else {

                }
            }.addOnFailureListener { error ->
                Log.i("TAG", "fetchUserData: ERROR = ${error.message}")
            }
    }

    private fun recyclerViewSetup() {
        adapter = AdminAdapter(this, list){ position, which ->
            if (which.equals("approve", true)) {
                val model = list[position]
                sessionManager.getUserId()?.let { updateStatus(it, true, model.email) }
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
    
    private fun updateStatus(id: String, isApprove: Boolean?, mail: String?) {
        firestore.collection("Users").document(id).update("accountStatus", isApprove).addOnCompleteListener { loaduser ->
            if (loaduser.isSuccessful) {
                sendMail(mail)
            }
        }.addOnFailureListener { error ->
            Log.i("TAG", "updateStatus: ERROR = ${error.message}")
        }
    }


    private fun sendMail(mail: String?) {
        val emailIntent = Intent().apply {
            action = Intent.ACTION_SEND
            data = Uri.parse("mailto:")
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
            putExtra(Intent.EXTRA_SUBJECT, "Eco Ray Solution")
            putExtra(Intent.EXTRA_TEXT, "Permission Granted Enter you pin")
        }
        if (emailIntent.resolveActivity(this.packageManager) != null) {
            emailIntent.setPackage("com.google.android.gm")
            startActivity(emailIntent)
        } else {
            Toast.makeText(this@AdminActivity, "No app available to send email!!", Toast.LENGTH_SHORT).show()
        }
    }
}