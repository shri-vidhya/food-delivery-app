package com.shri.doordashlite.restaurants.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferences(
    context: Context
) {
    private val mPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }

    fun setInt(key: String, value: Int?) {
        if (value != null) {
            mPrefs.edit().putInt(key, value).apply()
            removeSetting(key)
        }
    }

    fun getInt(key: String): Int? {
        if (!mPrefs.contains(key)) {
            return null
        }

        return mPrefs.getInt(key, 0)
    }

    fun getString(key: String): String? {
        return mPrefs.getString(key, null)
    }

    fun setString(key: String, value: String?) {
        mPrefs.edit().putString(key, value).apply()
    }

    fun removeSetting(key: String) {
        mPrefs.edit().remove(key).apply()
    }

    fun saveToFavourites(id: Int) {
        val set = mPrefs.getStringSet(FAV_KEY, HashSet())
        if(set?.contains(FAV_KEY) == false) {
            set.add(id.toString())
            mPrefs.edit { putStringSet(FAV_KEY, set) }
        }
    }

    fun deleteFromFavourites(id: Int) {
        val set = mPrefs.getStringSet(FAV_KEY, HashSet())
        if(set?.contains(FAV_KEY) == true) {
            set.remove(id.toString())
            mPrefs.edit { putStringSet(FAV_KEY, set) }
        }
    }

    fun getFromFavourites(): Set<String>? {
        return mPrefs.getStringSet(FAV_KEY, HashSet())
    }

    companion object {
        private const val SHARED_PREF_KEY = "app_data_store"
        private const val FAV_KEY = "favourites"
    }
}