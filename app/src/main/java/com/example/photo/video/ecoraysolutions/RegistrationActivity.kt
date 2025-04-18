package com.example.photo.video.ecoraysolutions

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.photo.video.ecoraysolutions.Models.SignUpModel
import com.example.photo.video.ecoraysolutions.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.otpview.OTPListener
import java.util.Calendar

class RegistrationActivity : AppCompatActivity() {
    private lateinit var otpText: String
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    private var currentPosition: String? = "Lahore"
    private var radiobutton: String? = null
    private var model: SignUpModel? = null
    lateinit var sessionManager: SessionManager
    lateinit var binding: ActivityRegistrationBinding
    private val cal by lazy { Calendar.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        binding.arrow.setOnClickListener {
            this.finish()
        }
        binding.linkadminlogin.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity,AdminLoginActivity::class.java))
            finish()
        }
        spinnerPerform()
        setUpForAuthentication()
        binding.dateofbirthtext.isFocusable = false
        binding.dateofbirthtext.isClickable = false
        binding.dateofbirthtext.setOnClickListener {
            openDatePickerDialog()
        }
        binding.otpView.requestFocus()
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                otpText = otp
                Toast.makeText(this@RegistrationActivity, "The OTP is $otp", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.voucherbtn.isChecked = true
        radiobutton = binding.voucherbtn.text.toString()

        binding.radiogroup.setOnCheckedChangeListener { radioGroup, id ->
            val rb = radioGroup.findViewById<RadioButton>(id)
            val isChecked: Boolean = rb.isChecked
            if (isChecked) {
                radiobutton = rb.text.toString()
                Toast.makeText(this, rb.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this, { cal: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                binding.dateofbirthtext.setText(selectedDate)
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )


        datePickerDialog.datePicker.maxDate = cal.getTimeInMillis()
        datePickerDialog.show()
    }


    private fun setUpForAuthentication() {
        binding.SignUp.setOnClickListener {
            val fname = binding.firstname.text.toString().trim()
            val lname = binding.lastnamebox.text.toString().trim()
            val email = binding.emaillogin.text.toString().trim()
            val calender = binding.dateofbirthtext.text.toString().trim()
            val phone = binding.pninput.text.toString().trim()
            val address = binding.addressinput.text.toString().trim()

            if (TextUtils.isEmpty(fname)) {
                showText("name is required")
            } else if (TextUtils.isEmpty(lname)) {
                showText("Last name is required")
            } else if (TextUtils.isEmpty(email)) {
                showText("Email is required")
            } else if (TextUtils.isEmpty(calender)) {
                showText("Birth of Date required")
            } else if (TextUtils.isEmpty(phone)) {
                showText("Mobile no is required")
            } else if (TextUtils.isEmpty(address)) {
                showText("Address is required")
            } else if (TextUtils.isEmpty(otpText)) {
                showText("pin is required")

            } else if (radiobutton.isNullOrEmpty()) {
                showText("must select RadioButton")
            } else if (TextUtils.isEmpty(fname) && (TextUtils.isEmpty(lname)) && (TextUtils.isEmpty(
                    email
                )) && (TextUtils.isEmpty(calender)) && (TextUtils.isEmpty(phone)) && (TextUtils.isEmpty(
                    address
                )) && (radiobutton.isNullOrEmpty())
            ) {
                showText("All fields are required")
            } else {
                if (fname.isNotEmpty() && lname.isNotEmpty() && email.isNotEmpty() && calender.isNotEmpty() && address.isNotEmpty() && otpText.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, "123456")
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val model =
                                    SignUpModel(
//                                        id = task.result.user?.uid,
                                        id = task.result.user?.uid,
                                        name = fname,
                                        father_name = lname,
                                        email = email,
                                        Birth_of_Date = calender,
                                        city = currentPosition,
                                        address = address,
                                        phoneNo = phone,
                                        password = otpText,
                                        type = radiobutton
                                    )
                                task.result.user?.uid?.let { it1 -> firestore.collection("Users").document(it1).set(model)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            sessionManager.setUserId(it1)
                                            sessionManager.setPin(otpText)
                                            showText("Wait for Admin response")
                                        }
                                    }.addOnFailureListener { e ->
                                        showText("${e.message}")
                                    }
                                }

                            }
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        }
                }
            }
            binding.firstname.setText("")
            binding.lastnamebox.setText("")
            binding.emaillogin.setText("")
            binding.dateofbirthtext.setText("")
            binding.pninput.setText("")
            binding.addressinput.setText("")
            binding.otpView.setOTP("")


        }

    }



    private fun spinnerPerform() {
        // City list
        val city = arrayOf(
            "Choose City",
            "Islamabad",
            "Lahore",
            "Multan",
            "Faisalabad",
            "Rawalpindi"
        )

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, city)
        binding.spin.adapter = arrayAdapter
        binding.spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentPosition = if (position ==0 ) {
                     null
                }else{
                 city[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun showText(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
}