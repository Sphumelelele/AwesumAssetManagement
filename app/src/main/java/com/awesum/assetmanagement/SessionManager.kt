package com.awesum.assetmanagement

import android.content.Context

/**
 * Tiny SharedPreferences wrapper that remembers the MySQL user_id
 * (from the `user` table in phpMyAdmin) that maps to the currently
 * signed-in Firebase account, so the rest of the app can attribute
 * created_by / linked_user_id without another network round trip.
 */
object SessionManager {
    private const val PREFS = "awesum_session"
    private const val KEY_USER_ID = "mysql_user_id"
    private const val KEY_NAME = "user_name"
    private const val KEY_EMAIL = "user_email"

    fun save(context: Context, userId: Int, name: String, email: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
            .putInt(KEY_USER_ID, userId)
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .apply()
    }

    fun getUserId(context: Context): Int =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(KEY_USER_ID, -1)

    fun getName(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_NAME, "") ?: ""

    fun getEmail(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_EMAIL, "") ?: ""

    fun clear(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().clear().apply()
    }
}
