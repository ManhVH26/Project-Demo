package com.example.projectdemo.domain.tracking

interface FirebaseTrackingManager {
    fun trackCustomEvent(eventName: String, parameters: Map<String, String> = emptyMap())
}