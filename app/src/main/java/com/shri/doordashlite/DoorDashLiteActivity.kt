package com.shri.doordashlite

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.shri.doordashlite.restaurants.RestaurantFinderFragment
import com.shri.doordashlite.restaurants.ui.RestaurantsViewModel
import com.shri.doordashlite.restaurants.ui.RestaurantsViewModelFactory

class DoorDashLiteActivity : AppCompatActivity() {
    lateinit var viewModel: RestaurantsViewModel
    private lateinit var progressOverlay: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, RestaurantsViewModelFactory()).get(RestaurantsViewModel::class.java)
        progressOverlay = findViewById(R.id.progress_bar)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, RestaurantFinderFragment.newInstance())
            .commit()
    }
}
