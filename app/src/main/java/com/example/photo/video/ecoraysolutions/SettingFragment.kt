package com.example.photo.video.ecoraysolutions

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.photo.video.ecoraysolutions.Models.SignUpModel
import com.example.photo.video.ecoraysolutions.databinding.FragmentSettingBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    lateinit var firestore: FirebaseFirestore
    lateinit var sessionManager: SessionManager
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        firestore = FirebaseFirestore.getInstance()
        sessionManager = SessionManager(requireContext())
        setUpFireStore()
        Linsetener()
        return binding.root
    }

    private fun Linsetener() {
        binding.card1.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
        binding.card2.setOnClickListener {
            startActivity(Intent(requireActivity(), ReportActivity::class.java))
        }
        binding.card5.setOnClickListener {
            dailogue()
        }

    }

    fun setUpFireStore() {
        if (sessionManager.getUserId()?.isNotEmpty() == true) {
            sessionManager.getUserId().let {
                if (it != null) {
                    firestore.collection("Users").document(it).get()
                        .addOnCompleteListener { task ->
                            val document = task.result
                            if (document.exists()) {
                                val name = document.getString("name") ?: "No name"
                                val email = document.getString("email") ?: "No email"

                                this.activity?.runOnUiThread {
                                    binding.nametext.text = name
                                    binding.emailText.text = email

                                }
                            } else {
                                Log.i("TAG", "Error getting Exception", task.exception)
                            }

                        }.addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT)
                                .show()

                        }
                }

            }

        }

    }
    fun dailogue() {
        val dailog = AlertDialog.Builder(requireContext(), R.style.TransparentAlertDialog)
        val inflate = layoutInflater.inflate(R.layout.deletealertdailog, null)
        dailog.setView(inflate)
        val alertdailog = dailog.create()
        val cancelBtn = inflate.findViewById<MaterialButton>(R.id.Cancel)
        val btnYes = inflate.findViewById<MaterialButton>(R.id.btnYes)
        cancelBtn.setOnClickListener {
            alertdailog.cancel()
        }
        btnYes.setOnClickListener {
            firebaseAuth.signOut()
            sessionManager.setLogin(false)
            startActivity(Intent(requireContext(),RegistrationActivity::class.java))
        }

        alertdailog.show()
    }

}