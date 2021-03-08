package com.shri.doordashlite.restaurants.network

import java.lang.Exception

data class UIResource<out T>(val status: Status, val data: T?, val message: String?, val exception: Exception?) {

    companion object {
        @JvmStatic
        fun <T> success(data: T? = null): UIResource<T> {
            return UIResource(Status.SUCCESS, data, null, null)
        }

        @JvmOverloads
        @JvmStatic
        fun <T> error(msg: String? = null, exception: Exception? = null, data: T? = null): UIResource<T> {
            return UIResource(Status.ERROR, data, msg, exception)
        }

        @JvmOverloads
        @JvmStatic
        fun <T> loading(data: T? = null): UIResource<T> {
            return UIResource(Status.LOADING, data, null, null)
        }
    }
}
