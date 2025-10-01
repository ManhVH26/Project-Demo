package com.example.projectdemo.domain.usecase

import com.example.projectdemo.domain.model.Auth
import com.example.projectdemo.data.repository.AuthRepository

class SilentLoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(deviceId: String): Result<Auth> {
        return authRepository.silentLogin(deviceId)
    }
}
