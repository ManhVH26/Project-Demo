package com.example.projectdemo.core.configuration

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.projectdemo.data.keys.RemoteKey

abstract class BaseRemoteConfiguration(
    private val sharedPrefs: SharedPreferences
) {
    protected abstract fun getAllKeys(): List<RemoteKey>

    protected fun saveLocal(key: RemoteKey, remoteValue: Any?) {

        sharedPrefs.edit {
            when (key) {
                is RemoteKey.BooleanKey -> {
                    val value = when (remoteValue) {
                        is Boolean -> remoteValue
                        is String -> {
                            when (remoteValue.lowercase()) {
                                "true", "1", "yes" -> true
                                "false", "0", "no" -> false
                                else -> key.defaultValue
                            }
                        }

                        else -> key.defaultValue
                    }
                    putBoolean(key.remoteKey, value)
                }

                is RemoteKey.StringKey -> {
                    val value = (remoteValue as? String)?.takeIf { it.isNotBlank() }
                        ?: key.defaultValue
                    putString(key.remoteKey, value)
                }

                is RemoteKey.IntKey -> {
                    val value = when (remoteValue) {
                        is Number -> remoteValue.toInt()
                        is String -> remoteValue.toIntOrNull() ?: key.defaultValue
                        else -> key.defaultValue
                    }
                    putInt(key.remoteKey, value)
                }
            }
        }
    }

    fun syncAllConfigs(remoteMap: Map<String, Any?>) {
        getAllKeys().forEach { key ->
            val remoteValue = remoteMap[key.remoteKey]
            saveLocal(key, remoteValue)
        }
    }

    protected fun RemoteKey.BooleanKey.get(): Boolean {
        return sharedPrefs.getBoolean(remoteKey, defaultValue)
    }

    protected fun RemoteKey.StringKey.get(): String {
        return sharedPrefs.getString(remoteKey, defaultValue) ?: defaultValue
    }

    protected fun RemoteKey.IntKey.get(): Int {
        return sharedPrefs.getInt(remoteKey, defaultValue)
    }
}

