package com.shri.doordashlite.restaurants

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shri.doordashlite.R
import com.shri.doordashlite.restaurants.data.UserPreferences
import com.shri.doordashlite.restaurants.data.network.Status
import com.shri.doordashlite.restaurants.ui.RestaurantsViewModel

class RestaurantFinderFragment : Fragment() {
    private lateinit var restaurantList: RecyclerView
    private lateinit var progressOverlay: RelativeLayout
    private lateinit var initialLoader: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var errorTextView: TextView
    private var recyclerViewAdapter: RestaurantListAdapter? = null
    private val viewModel: RestaurantsViewModel by activityViewModels()
    private lateinit var sharedPref : UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = UserPreferences(requireContext())
        return inflater.inflate(R.layout.restaurant_finder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initRecyclerView(view)
        loadData()
    }

    private fun initViews() {
        progressOverlay = requireActivity().findViewById(R.id.progress_overlay)
        errorTextView = requireActivity().findViewById(R.id.error_text)
        swipeRefreshLayout = requireActivity().findViewById(R.id.swipeToRefresh)
        initialLoader = requireActivity().findViewById(R.id.initial_loader)

        swipeRefreshLayout.setOnRefreshListener {
            Log.d(TAG, "Swiped up to Refresh")
            loadData()
        }
    }

    private fun initRecyclerView(view: View) {
        restaurantList = view.findViewById(R.id.restaurant_list)
        restaurantList.setHasFixedSize(true)
        restaurantList.layoutManager = LinearLayoutManager(activity)
    }

    private fun loadData() {
        val favourites = sharedPref.getFromFavourites()
        recyclerViewAdapter = RestaurantListAdapter(sharedPref, favourites)
        restaurantList.adapter = recyclerViewAdapter
        val limit = 50
        val offset = 0
        val lat = 37.422740
        val lng = -122.139956
        viewModel.getRestaurantsList(lat, lng, limit, offset).observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    initialLoader.visibility = View.GONE
                    progressOverlay.visibility = View.GONE
                    errorTextView.visibility = View.GONE
                    if (recyclerViewAdapter != null) {
                        recyclerViewAdapter!!.setAdapterList(it.data)
                        recyclerViewAdapter!!.notifyDataSetChanged()
                    }
                }
                Status.LOADING -> {
                    if (initialLoader.visibility == View.GONE) {
                        progressOverlay.visibility = View.VISIBLE
                        errorTextView.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    errorTextView.text = it.message
                    errorTextView.visibility = View.VISIBLE
                    initialLoader.visibility = View.GONE
                    progressOverlay.visibility = View.GONE
                }
            }
            swipeRefreshLayout.isRefreshing = false
        })
    }

    companion object {
        const val TAG = "RestaurantFinderFragment"

        fun newInstance(): RestaurantFinderFragment {
            return RestaurantFinderFragment()
        }
    }
}
