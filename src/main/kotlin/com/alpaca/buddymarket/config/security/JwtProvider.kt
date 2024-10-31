package com.alpaca.buddymarket.config.security

import com.alpaca.buddymarket.auth.dto.TokenData
import com.alpaca.buddymarket.config.exception.ErrorCode
import com.alpaca.buddymarket.config.exception.MyException
import com.alpaca.buddymarket.config.properties.JwtProps
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.SignatureException
import java.time.Instant
import java.util.Arrays
import java.util.Date
import java.util.stream.Collectors

@Service
class JwtProvider(
    jwtProps: JwtProps,
) {
    private final val accessTokenExpiration = 60 * 60 * 60 // 1 hour MS

    private final val refreshTokenExpiration = accessTokenExpiration * 24 * 15 // 15 days MS

    val key = Keys.hmacShaKeyFor(jwtProps.secretKey.toByteArray(StandardCharsets.UTF_8))

    fun generateToken(
        userId: Long,
        authority: String = "ROLE_USER",
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
            makeExpiration(expiration),
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
        authorities: String = "ROLE_USER",
        expiration: Int = refreshTokenExpiration,
    ): String = generateJwtToken(userId, makeExpiration(expiration), authorities)

    private fun generateJwtToken(
        userId: Long,
        expiration: Date,
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
            .setExpiration(expiration)
            .signWith(key)
            .compact()
    }

    fun validateJwtToken(token: String): Boolean {
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)

            return true
        } catch (signatureException: SignatureException) {
            throw MyException(ErrorCode.INVALID_TOKEN, "Invalid token")
        } catch (expiredJwtException: ExpiredJwtException) {
            throw MyException(ErrorCode.EXPIRED_TOKEN, "Expired token")
        } catch (e: Exception) {
            throw MyException(ErrorCode.INVALID_TOKEN, e.message)
        }
    }

    fun getAuthentication(token: String): Authentication {
        val claims =
            Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body

        val authorities =
            Arrays
                .stream(claims["roles"].toString().split(",").toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role?.let { "USER" }) }
                .collect(Collectors.toList())

        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
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

    fun getUserIdFromToken(token: String): Long = getBodyFromToken(token).subject.toLong()

    private fun makeExpiration(expiration: Int): Date = Date.from(Instant.now().plusSeconds(expiration.toLong()))
}
