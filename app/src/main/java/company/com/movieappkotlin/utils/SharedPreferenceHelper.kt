package company.com.movieappkotlin.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(context: Context) {
    val sharedPref: SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        sharedPref.edit().putString(KEY_NAME, text).apply()
    }

    fun save(KEY_NAME: String, value: Int) {
        sharedPref.edit().putInt(KEY_NAME, value).apply()

    }

    fun save(KEY_NAME: String, status: Boolean) {

        sharedPref.edit().putBoolean(KEY_NAME, status).apply()

    }

    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, null)

    }

    fun getValueInt(KEY_NAME: String): Int {

        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {

        return sharedPref.getBoolean(KEY_NAME, defaultValue)

    }

    fun clearSharedPreference() {
        sharedPref.edit().clear().apply()
    }

    fun removeValue(KEY_NAME: String) {
        sharedPref.edit().remove(KEY_NAME).apply()
    }

}