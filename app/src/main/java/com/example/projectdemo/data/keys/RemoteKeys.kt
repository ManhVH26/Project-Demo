package com.example.projectdemo.data.keys

sealed class RemoteKey(val remoteKey: String) {
    abstract val defaultValue: Any

    class BooleanKey(
        remoteKey: String,
        override val defaultValue: Boolean
    ) : RemoteKey(remoteKey)

    class StringKey(
        remoteKey: String,
        override val defaultValue: String
    ) : RemoteKey(remoteKey)

    class IntKey(
        remoteKey: String,
        override val defaultValue: Int
    ) : RemoteKey(remoteKey)

    companion object {
        val AppTitle = StringKey("app_title", "Project Demo")
        val IsFeatureEnabled = BooleanKey("is_feature_enabled", true)
        val MaxRetryCount = IntKey("max_retry_count", 5)

        fun getAllKeys() = listOf(AppTitle, IsFeatureEnabled, MaxRetryCount)
    }
}

