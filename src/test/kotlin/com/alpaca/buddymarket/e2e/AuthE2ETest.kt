package com.alpaca.buddymarket.e2e

import com.alpaca.buddymarket.auth.dto.RefreshTokenRequest
import com.alpaca.buddymarket.auth.dto.SocialLoginRequest
import com.alpaca.buddymarket.user.entity.SnsType
import com.heo.mbti.base.BaseE2E
import com.heo.mbti.base.faker
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Description
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthE2ETest : BaseE2E() {
    val url = "/v1/auth"

    @BeforeAll
    fun beforeAll() {
        initializeTEST()
    }

    @Nested
    @Description("소셜 로그인")
    inner class SocialLogin {
        @Test
        fun `가입 + 로그인`() {
            // given
            val dto =
                SocialLoginRequest(
                    snsId = faker.number().digits(10).toString(),
                    email = faker.internet().emailAddress(),
                    name = faker.name().firstName(),
                    snsType = SnsType.KAKAO,
                )

            // when
            val response =
                mockMvc.perform(
                    post("/v1/auth/social-login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsBytes(dto)),
                )

            // then
            response.andExpectAll(
                status().isOk,
                jsonPath("$.accessToken").exists(),
                jsonPath("$.refreshToken").exists(),
            )
        }

        @Test
        fun `이미 가입되어 있어 로그인만`() {
            // given
            val dto =
                SocialLoginRequest(
                    snsId = faker.number().digits(10).toString(),
                    email = faker.internet().emailAddress(),
                    name = faker.name().firstName(),
                    snsType = SnsType.KAKAO,
                )

            // when
            val authPostRequest = authPostRequest("$url/social-login", dto)

            // then
            authPostRequest
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(status().isOk, jsonPath("$.accessToken").exists(), jsonPath("$.refreshToken").exists())
        }
    }

    @Nested
    @Description("토큰 재발급 ")
    inner class RefreshToken {
        @Test
        fun `성공`() {
            // given
            val refreshToken = jwtService.generateRefreshToken(user.id)
            val dto = RefreshTokenRequest(refreshToken)

            // when
            val post =
                mockMvc.perform(
                    post("$url/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(dto)),
                )

            // then
            post
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(status().isOk, jsonPath("$.accessToken").exists())
        }

        @Test
        fun `만료`() {
            // given
            val refreshToken = jwtService.generateRefreshToken(user.id, "ROLE_USER", -10)
            val dto = RefreshTokenRequest(refreshToken)

            // when
            val post =
                mockMvc
                    .perform(
                        post("$url/refresh-token")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsBytes(dto)),
                    )

            // then

            post
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(status().isUnauthorized)
        }
    }
}
