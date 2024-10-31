package com.alpaca.buddymarket.config.security

import MyException
import com.alpaca.buddymarket.config.exception.ErrorCode
import com.alpaca.buddymarket.config.properties.JwtProps
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.SignatureException
import java.time.Instant
<<<<<<< Updated upstream:src/main/kotlin/com/alpaca/buddymarket/config/security/JwtService.kt
import java.util.Date
=======
import java.util.Arrays
import java.util.Date
import java.util.stream.Collectors
>>>>>>> Stashed changes:src/main/kotlin/com/alpaca/buddymarket/config/security/JwtProvider.kt

@Service
class JwtService(
    jwtProps: JwtProps,
) {
    private final val accessTokenExpiration = 60 * 60 * 60 // 1 hour MS

    private final val refreshTokenExpiration = accessTokenExpiration * 24 * 15 // 15 days MS

    val key = Keys.hmacShaKeyFor(jwtProps.secretKey.toByteArray(StandardCharsets.UTF_8))

    data class TokenData(
        val accessToken: String,
        val refreshToken: String? = null,
    )

    fun generateToken(
        userId: Long,
        authority: String,
        withRefreshToken: Boolean = true,
    ): TokenData =
        TokenData(
            generateAccessToken(userId, authority),
            if (withRefreshToken) generateRefreshToken(userId, authority) else null,
        )

    fun generateAccessToken(
        userId: Long,
        authorities: String,
        expiration: Int = accessTokenExpiration,
    ): String =
        generateJwtToken(
            userId,
            expiration,
            authorities,
        )

    fun extractTokenFrom(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }

    fun generateRefreshToken(
        userId: Long,
        authorities: String,
        expiration: Int = refreshTokenExpiration,
    ): String = generateJwtToken(userId, expiration, authorities)

    private fun generateJwtToken(
        userId: Long,
        expiration: Int,
        authorities: String,
    ): String {
        val map = HashMap<String, Any>()
        map["userId"] = userId
        map["authorities"] = authorities

        return Jwts
            .builder()
            .setSubject(userId.toString())
            .addClaims(map)
            .setIssuedAt(Date())
            .setExpiration(makeExpiration(expiration))
            .signWith(key)
            .compact()
    }

    fun getBodyFromToken(token: String): Claims =
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (signatureException: SignatureException) {
            throw MyException(ErrorCode.INVALID_TOKEN, "Invalid token")
        } catch (expiredJwtException: ExpiredJwtException) {
            throw MyException(ErrorCode.EXPIRED_TOKEN, "Expired token")
        } catch (e: Exception) {
            throw MyException(ErrorCode.INVALID_TOKEN, e.message)
        }

    private fun makeExpiration(expiration: Int): Date = Date.from(Instant.now().plusSeconds(expiration.toLong()))
}
