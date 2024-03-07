package com.example.mvvmnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvmnewsapp.R
import com.example.mvvmnewsapp.util.ConnectivityObserver
import com.example.mvvmnewsapp.util.NetworkConnectivityObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*connectivityObserver = NetworkConnectivityObserver(applicationContext)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                connectivityObserver.observe().collect {
                    Toast.makeText(applicationContext, "Status is $it", Toast.LENGTH_LONG).show()
                    Log.i("testing internet", "Status is $it")
                }
            }
        }*/


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