package com.gameloop.laboratorioclinicoproc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.gameloop.laboratorioclinicoproc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpNavbar()
        setUpBottomNavBar()
    }

    private fun setUpBottomNavBar() {
        val navHost = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        navHost?.let {
            binding.bnMain.setupWithNavController(navHost.findNavController())
        }
    }

    private fun setUpNavbar() {
        val navHost = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        navHost?.let {
            val navController = navHost.findNavController()
            NavigationUI.setupActionBarWithNavController(this, navController)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.navHostFragment.findNavController()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}