package com.example.photo.video.ecoraysolutions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.photo.video.ecoraysolutions.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    lateinit var binding: ActivityReportBinding
    private var currentPosition: String = "Jazz Cash"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinnerPerform()

    }

    private fun spinnerPerform() {
        // PaymentMethod list
        val city = arrayOf(
            "maintenance services",
            "inverter checks",
            "Panel Cleaning"
        )

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, city)
        binding.spinner.adapter = arrayAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentPosition = (if (position == 0) {
                    null
                } else {
                    city[position]
                }).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}