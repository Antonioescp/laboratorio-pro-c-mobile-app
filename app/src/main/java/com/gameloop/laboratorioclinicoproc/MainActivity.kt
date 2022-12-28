package com.gameloop.laboratorioclinicoproc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.gameloop.laboratorioclinicoproc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpActionBar()
        setUpBottomNavBar()
    }

    private fun setUpBottomNavBar() {
        val navHost = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        navHost?.let {
            val navController = navHost.findNavController()
            binding.bnMain.setupWithNavController(navController)

            // Showing nav bar only on top level destinations
            navController.addOnDestinationChangedListener { _, destination, _ ->
                binding.bnMain.visibility = when (destination.id) {
                    R.id.myPatientsFragment -> View.VISIBLE
                    R.id.testsFragment -> View.VISIBLE
                    else -> View.GONE
                }
            }
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.appBar)
        val navHost = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        navHost?.let {
            NavigationUI.setupActionBarWithNavController(this, navHost.findNavController())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.navHostFragment.findNavController()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}