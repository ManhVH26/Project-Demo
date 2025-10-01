package com.example.projectdemo.domain.mapper

import com.example.projectdemo.data.model.AuthData
import com.example.projectdemo.domain.model.Auth

/**
 * Mapper để convert từ API response sang domain model
 */
object AuthMapper {
    
    /**
     * Convert AuthData (API response) sang Auth (domain model)
     */
    fun toDomain(authData: AuthData): Auth {
        return Auth(
            tokenType = authData.tokenType,
            accessToken = authData.accessToken,
            refreshToken = authData.refreshToken,
            expiresIn = authData.expiresIn,
            refreshExpiresIn = authData.refreshExpiresIn
        )
    }
}
