package com.example.financetracker.utils
import android.content.Context
import android.content.SharedPreferences
import com.example.financetracker.data.User

object SharedPreferencesUtils {
    private const val PREFS_FILE_NAME = "FinanceTrackerPrefs"
    private const val KEY_EMAIL = "email"
    private const val KEY_NAME = "name"

    fun saveUser(context: Context, user: User) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(KEY_EMAIL, user.email)
            putString(KEY_NAME, user.name)
            apply()
        }
    }

    fun getUser(context: Context): User {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val email = prefs.getString(KEY_EMAIL, "")
        val name = prefs.getString(KEY_NAME, "")
        return User(email ?: "", "", name ?: "")
    }

    fun clearUser(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            remove(KEY_EMAIL)
            remove(KEY_NAME)
            apply()
        }
    }
}