package com.shri.doordashlite.restaurants.data.network

import com.google.gson.GsonBuilder
import com.shri.doordashlite.restaurants.data.model.RestaurantDTO
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


open class RestaurantsAPI {
    interface Api {
        /**
         * Get restaurant list for the given location
         * @param startDate - training plan start date
         * @param longWorkoutDay - long ride day (ISO day of week)
         */
        @GET("/v1/store_feed/?lat=37.422740&lng=-122.139956&offset=0&limit=50")
        suspend fun getStoreListAsync(
            @Query("lat") latitude: Double,
            @Query("lng") longitude: Double,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int
        ): Response<RestaurantDTO>
    }

    open suspend fun getStoreList(lat: Double, lng: Double, offset: Int, limit: Int): Response<RestaurantDTO> {
        return webservice().getStoreListAsync(lat, lng, offset, limit)
    }

    companion object {
        private const val BASE_URL = "https://api.doordash.com"

        private fun webservice(): Api {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(Api::class.java)
        }
    }
}
