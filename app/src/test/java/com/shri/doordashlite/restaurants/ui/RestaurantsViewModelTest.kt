package com.shri.doordashlite.restaurants.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.shri.doordashlite.restaurants.data.model.RestaurantDTO
import com.shri.doordashlite.restaurants.data.model.Store
import com.shri.doordashlite.restaurants.data.network.RestaurantsAPI
import com.shri.doordashlite.restaurants.data.network.Status
import com.shri.doordashlite.restaurants.data.network.UIResource
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class RestaurantsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: RestaurantsViewModel
    private val storeList = ArrayList<Store>()
    private val restaurantDTO = RestaurantDTO(false, 1, 1, false, "", storeList)
    private val errorResponse: Response<RestaurantDTO> = Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "{\"key\":[\"test\"]}"))
    private val successResponse = Response.success(restaurantDTO)
    private val loadingStatus = UIResource(Status.LOADING, null, null, null)
    private val errorStatus = UIResource(Status.ERROR, null, "Response.error()", null)
    private val successStatus = UIResource(Status.SUCCESS, storeList, null, null)
    private lateinit var restaurantsAPI: RestaurantsAPI

    var observer: Observer<UIResource<List<Store?>?>> = mock()

    @Before
    fun before() {
        restaurantsAPI = Mockito.mock(RestaurantsAPI::class.java)
        runBlocking {
            Mockito.`when`(restaurantsAPI.getStoreList(37.422740, -122.139956, 0, 50)).thenReturn(successResponse)
            Mockito.`when`(restaurantsAPI.getStoreList(37.422740, -122.139956, 0, 0)).thenReturn(errorResponse)
        }
        viewModel = RestaurantsViewModel(restaurantsAPI)
        viewModel.storeListPostStatus.observeForever(observer)
    }

    @Test
    fun shouldReturnSuccess() {
        viewModel.getRestaurantsList(37.422740, -122.139956, 0, 50)
        verify(observer, timeout(50)).onChanged(loadingStatus)
        verify(observer, timeout(50)).onChanged(successStatus)
    }

    @Test
    fun shouldReturnFailure() {
        viewModel.getRestaurantsList(37.422740, -122.139956, 0, 0)
        verify(observer, timeout(50)).onChanged(loadingStatus)
        verify(observer, timeout(50)).onChanged(errorStatus)
    }
}
