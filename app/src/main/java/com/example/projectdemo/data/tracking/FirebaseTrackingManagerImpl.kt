package com.example.projectdemo.data.tracking

import android.util.Log
import com.example.projectdemo.domain.tracking.FirebaseTrackingManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent


class FirebaseTrackingManagerImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : FirebaseTrackingManager {

    override fun trackCustomEvent(eventName: String, parameters: Map<String, String>) {
        try {
            firebaseAnalytics.logEvent(eventName) {
                parameters.forEach { (key, value) ->
                    param(key, value)
                }
            }
        } catch (e: Exception) {
            Log.e("FirebaseTrackingManager", "Error tracking event: ${e.message}")
        }
    }
}