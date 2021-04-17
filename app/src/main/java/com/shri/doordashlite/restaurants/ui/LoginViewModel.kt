package com.shri.doordashlite.restaurants.ui

import androidx.lifecycle.*
import com.shri.doordashlite.restaurants.data.model.LoginResponse
import com.shri.doordashlite.restaurants.data.network.RestaurantsAPI
import com.shri.doordashlite.restaurants.data.network.UIResource
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: RestaurantsAPI) : ViewModel() {

    private val _loginResponse: MutableLiveData<UIResource<LoginResponse>?> = MutableLiveData()
    val loginResponse: LiveData<UIResource<LoginResponse>?>
        get() = _loginResponse

    fun login(email: String, password: String) {
        _loginResponse.value = UIResource.loading()
        viewModelScope.launch {
            val map: HashMap<String, String> = HashMap()
            map["email"] = email
            map["password"] = password
            val response = repo.login(map)
            if(response.isSuccessful) {
                _loginResponse.value = UIResource.success(response.body())
            } else {
                _loginResponse.value = UIResource.error("Login failure")
            }
        }

    }

}

class LoginViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val restaurantsAPI = RestaurantsAPI()
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(restaurantsAPI) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}