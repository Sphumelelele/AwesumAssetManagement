package com.awesum.assetmanagement.ui.auth

import android.content.Context
import android.util.Log
import com.awesum.assetmanagement.SessionManager
import com.awesum.assetmanagement.api.ApiClient
import com.google.firebase.auth.FirebaseUser

/**
 * After ANY successful Firebase sign-in (email/password, register, or Google),
 * push the user into the MySQL `user` table (via phpMyAdmin-managed DB) and
 * cache the resulting MySQL user_id locally.
 */
object AuthHelper {
    private const val TAG = "AuthHelper"

    suspend fun syncToMySql(context: Context, firebaseUser: FirebaseUser, provider: String) {
        val name = firebaseUser.displayName ?: firebaseUser.email?.substringBefore("@") ?: "Installer"
        val email = firebaseUser.email ?: ""
        if (email.isEmpty()) return

        try {
            val res = ApiClient.instance.syncUser(
                firebaseUid = firebaseUser.uid,
                name = name,
                email = email,
                authProvider = provider,
                role = "Installer"
            )
            val data = res.body()?.data
            if (res.isSuccessful && res.body()?.success == true && data != null) {
                SessionManager.save(context, data.user_id, data.name, data.email)
                Log.d(TAG, "User synced successfully: ${data.user_id}")
            } else {
                Log.e(TAG, "Sync failed: ${res.body()?.message ?: res.message()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Sync error: ${e.message}", e)
        }
    }
}
