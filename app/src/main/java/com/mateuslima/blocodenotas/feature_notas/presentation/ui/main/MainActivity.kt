package com.mateuslima.blocodenotas.feature_notas.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.databinding.ActivityMainBinding
import com.mateuslima.blocodenotas.feature_notas.presentation.util.NotasDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onBackPressed() {
        if(navController.currentDestination?.id == R.id.homeNotesFragment){
            NotasDialog.sairApp(this){sair -> if (sair) finish()}
        }else super.onBackPressed()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}