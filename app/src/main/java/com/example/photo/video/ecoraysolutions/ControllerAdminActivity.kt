package com.example.photo.video.ecoraysolutions

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHost
import androidx.navigation.ui.setupWithNavController
import com.example.photo.video.ecoraysolutions.databinding.ActivityControllerAdminBinding

class ControllerAdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityControllerAdminBinding
    private val navController by lazy {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerAdmin) as NavHost
        navHost.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControllerAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigation()
    }
    private fun bottomNavigation(){
        binding.navAdmin.setupWithNavController(navController)
    }
}