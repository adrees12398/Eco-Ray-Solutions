package com.example.photo.video.ecoraysolutions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.photo.video.ecoraysolutions.databinding.FragmentOfferBinding

class OfferFragment : Fragment() {
    lateinit var binding:FragmentOfferBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfferBinding.inflate(layoutInflater,container,false)
        Glide.with(requireContext()).asGif().load(R.drawable.imagefig).into(binding.imageView)
        return binding.root
    }
    fun spinner(){
        val packages = arrayOf("Alpha Solar Packages","Premier Energy Packages")
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,packages)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


    }


}