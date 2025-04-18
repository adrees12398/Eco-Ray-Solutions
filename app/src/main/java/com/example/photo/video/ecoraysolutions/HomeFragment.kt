package com.example.photo.video.ecoraysolutions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photo.video.ecoraysolutions.Adapters.PanelDetailAdapter
import com.example.photo.video.ecoraysolutions.Models.PanelModel
import com.example.photo.video.ecoraysolutions.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: PanelDetailAdapter
    private var model = ArrayList<PanelModel>()
    lateinit var firebaseAuth: FirebaseAuth

    val sessionManager by lazy {
        SessionManager(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        recyclerViewSetup()
        spinner1()
        spinner2()
        spinner3()
        spinner4()
        return binding.root
    }
    private fun spinner1() {
        val capacity = arrayOf(
            "Capacity",
            "Capacity:30Kwh",
            "Capacity:30Kwh",
            "Capacity:30Kwh",
            "Capacity:30Kwh"
        )
        val spinnerAdapter1 =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, capacity)
        binding.spinner1.adapter = spinnerAdapter1
        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
    private fun spinner2(){
        val price = arrayOf(
            "price",
            "price:$300",
            "price:$600",
            "price:$700",
            "price:$200"
        )
        val priceAdapter =ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,price)
        binding.spinner2.adapter = priceAdapter
        binding.spinner2.onItemSelectedListener =
            object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
    private fun spinner3(){
        val brand = arrayOf(
            "Brand",
            "Longi Solar",
            "Trina Solar",
            "JA Solar",
            "Jinko Solar",
            "Canadian Solar"
        )
     val brandAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,brand)
        binding.spinner3.adapter = brandAdapter
        binding.spinner3.onItemSelectedListener = object  : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }
    private fun spinner4(){
        val brand = arrayOf(
            "Installation type",
            "rooftop",
            "ground",
            "carport mounts",
            "Pole"
        )
        val brandAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,brand)
        binding.spinner4.adapter = brandAdapter
        binding.spinner4.onItemSelectedListener = object  : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun recyclerViewSetup() {
        adapter = PanelDetailAdapter(requireContext(), model)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
        model.add(
            PanelModel(
                "High-efficiency Solar Panel", R.drawable.solarimage, "price:250", "capacity:300Kwh"
            )
        )
        model.add(
            PanelModel(
                "durable inverter", R.drawable.durable_inverter2, "price:350", "capacity:360Kwh"
            )
        )
        model.add(
            PanelModel(
                "solar better image", R.drawable.solar_better_image, "price:280", "capacity:300Kwh"
            )
        )
        adapter.notifyDataSetChanged()

    }
}