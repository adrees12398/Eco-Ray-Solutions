package com.example.photo.video.ecoraysolutions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photo.video.ecoraysolutions.databinding.FragmentCarrBinding

class CarrFragment : Fragment() {
    lateinit var binding: FragmentCarrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarrBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    fun numberPicker(){

    }
}