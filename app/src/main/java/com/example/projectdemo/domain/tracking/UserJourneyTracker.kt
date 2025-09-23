package com.example.projectdemo.domain.tracking

import android.util.Log

class UserJourneyTracker(
    private val firebaseTrackingManager: FirebaseTrackingManager
) {

    companion object {
        private val TAG = UserJourneyTracker::class.java.simpleName
    }

    private var splashStartTime: Long = 0L

    fun trackSplashView() {
        Log.d(TAG, "trackSplashView")
        splashStartTime = System.currentTimeMillis()
        firebaseTrackingManager.trackCustomEvent("splash_view")
    }

    fun trackMainView() {
        Log.d(TAG, "trackMainView")
        firebaseTrackingManager.trackCustomEvent("main_view")

        if (splashStartTime > 0) {
            val duration = System.currentTimeMillis() - splashStartTime
            trackSplashToMainTiming(duration)
        }
    }

    private fun trackSplashToMainTiming(durationMs: Long) {
        Log.d(TAG, "trackSplashToMainTiming: ${durationMs}ms")
        firebaseTrackingManager.trackCustomEvent(
            "splash_to_main_timing",
            mapOf(
                "duration_ms" to durationMs.toString(),
                "duration_seconds" to (durationMs / 1000).toString()
            )
        )
    }

    fun trackButtonClick(buttonName: String) {
        Log.d(TAG, "trackButtonClick: $buttonName")
        firebaseTrackingManager.trackCustomEvent(
            "button_click",
            mapOf("button_name" to buttonName)
        )
    }

    fun trackFeatureUsed(featureName: String) {
        Log.d(TAG, "trackFeatureUsed: $featureName")
        firebaseTrackingManager.trackCustomEvent(
            "feature_used",
            mapOf("feature_name" to featureName)
        )
    }
}
