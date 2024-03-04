package com.example.mvvmnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.db.ArticleDatabase
import com.example.mvvmnewsapp.repository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>( R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { item->
            navController.navigate(item.itemId)
            Log.i("NewsActivity", "item id ${item.itemId}" +
                    "newslistid ${R.id.newsListFragment}")
            true
        }

/*
        val onDestinationChangedListener = NavController.OnDestinationChangedListener{
                controller, destination, arguments ->
            if (bottomNavigationView.selectedItemId!=controller.currentDestination?.id){
                bottomNavigationView.selectedItemId = controller.currentDestination!!.id
            }
            Log.i("NewsActivity", "${controller.currentDestination?.id}")


        }
        navHostFragment.findNavController().addOnDestinationChangedListener(onDestinationChangedListener)

*/



    }




    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }




}