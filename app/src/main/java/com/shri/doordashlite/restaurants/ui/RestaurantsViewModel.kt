package com.shri.doordashlite.restaurants.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shri.doordashlite.restaurants.model.Store
import com.shri.doordashlite.restaurants.network.RestaurantsAPI
import com.shri.doordashlite.restaurants.network.UIResource
import java.io.IOException
import java.lang.Exception
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RestaurantsViewModel(private val repo: RestaurantsAPI) : ViewModel() {

    val storeListPostStatus: MutableLiveData<UIResource<List<Store?>?>> = MutableLiveData()

    fun getRestaurantsList(lat: Double, lng: Double, offset: Int, limit: Int): MutableLiveData<UIResource<List<Store?>?>> {
        storeListPostStatus.postValue(UIResource.loading(null))
        viewModelScope.launch {
            try {
                val response = repo.getStoreList(lat, lng, offset, limit)
                if (response.isSuccessful && response.body() != null)
                    storeListPostStatus.postValue(UIResource.success(response.body()!!.stores))
                else
                    storeListPostStatus.postValue(UIResource.error(response.message(), null, null))
            } catch (e: HttpException) {
                Log.e(TAG, e.toString())
                storeListPostStatus.postValue(
                    UIResource.error(
                        "Error Occurred while fetching Stores list: $e",
                        e,
                        null
                    )
                )
            } catch (e: IOException) {
                Log.e(TAG, e.toString())
                storeListPostStatus.postValue(
                    UIResource.error(
                        "Network not available error message: $e",
                        e,
                        null
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                storeListPostStatus.postValue(
                    UIResource.error(
                        "Error Occurred while fetching Stores List: $e",
                        e,
                        null
                    )
                )
            }
        }
        return storeListPostStatus
    }

    companion object {
        const val TAG = "RestaurantsViewModel"
    }
}

class RestaurantsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val restaurantsAPI = RestaurantsAPI()
        if (modelClass.isAssignableFrom(RestaurantsViewModel::class.java)) {
            return RestaurantsViewModel(restaurantsAPI) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
