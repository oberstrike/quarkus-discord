package de.core.domain

import java.math.BigDecimal


data class JWTToken(
        val accessToken: String = "",
        val refreshToken: String = "",
        val refreshExpiresIn: BigDecimal = BigDecimal.ZERO,
        val scope: String = "",
        val tokenType: String = "",
        val sessionState: String = "",
        val expiresIn: BigDecimal = BigDecimal.ZERO
)