package com.shri.doordashlite.restaurants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.shri.doordashlite.R
import com.shri.doordashlite.restaurants.data.UserPreferences
import com.shri.doordashlite.restaurants.data.network.Status
import com.shri.doordashlite.restaurants.data.network.UIResource
import com.shri.doordashlite.restaurants.ui.LoginViewModel
import com.shri.doordashlite.restaurants.ui.LoginViewModelFactory
import com.shri.doordashlite.restaurants.ui.RestaurantsViewModel
import com.shri.doordashlite.restaurants.ui.RestaurantsViewModelFactory
import kotlinx.android.synthetic.main.login_screen.*

class LoginFragment: Fragment() {
    private lateinit var sharedPref: UserPreferences
    private lateinit var initialLoader: View
    private lateinit var progressOverlay: View
    private lateinit var submitButton: View
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sharedPref = UserPreferences(requireContext())
        viewModel = ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)
        return inflater.inflate(R.layout.login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
//        loadData()
    }

    fun initViews() {
        initialLoader = requireActivity().findViewById(R.id.initial_loader)
        email = requireActivity().findViewById(R.id.email_address)
        password = requireActivity().findViewById(R.id.password_value)
        submitButton = requireActivity().findViewById(R.id.login_button)
        progressOverlay = requireActivity().findViewById(R.id.progress_overlay)


        initialLoader.visibility = View.GONE
        submitButton.setOnClickListener {
            val emailAddress = email.text.toString().trim()
            val passwordValue = password.text.toString().trim()
            viewModel.login(emailAddress, passwordValue)
        }

        viewModel.loginResponse.observe(viewLifecycleOwner, {
            when(it?.status) {
                Status.SUCCESS -> {
                    progressOverlay.visibility = View.GONE
//                    childFragmentManager.beginTransaction()
//                            .replace(R.id.content_frame, RestaurantFinderFragment.newInstance())
//                            .commit()
                }
                Status.LOADING -> {
                    progressOverlay.visibility = View.VISIBLE
                    submitButton.isEnabled = false
                }
                Status.ERROR -> {
                    progressOverlay.visibility = View.GONE
                    submitButton.isEnabled = true
                    // TODO: Show error
                }
            }
        })
    }

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}