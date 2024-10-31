package com.heo.mbti.base

import com.alpaca.buddymarket.auth.dto.TokenData
import com.alpaca.buddymarket.config.security.JwtProvider
import com.alpaca.buddymarket.factory.UserFactory
import com.alpaca.buddymarket.user.UserRepository
import com.alpaca.buddymarket.user.entity.User
import com.fasterxml.jackson.databind.ObjectMapper
import net.datafaker.Faker
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.transaction.annotation.Transactional

val faker = Faker()

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BaseE2E {
    val objectMapper = ObjectMapper()
    lateinit var token: TokenData
    lateinit var user: User
    lateinit var userFactory: UserFactory

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var jwtService: JwtProvider

    @Autowired
    lateinit var userRepository: UserRepository

    fun initializeTEST() {
        userFactory = UserFactory(userRepository)
        initMember()
        initToken()
    }

    fun initMember() {
        user = userFactory.makeUser()
    }

    fun initToken() {
        token = jwtService.generateToken(user.id)
    }

    fun authGetRequest(
        url: String,
        pathParams: Any? = null,
    ): ResultActions =
        mockMvc.perform(
            get(url, pathParams)
                .header("Authorization", "Bearer ${token.accessToken}")
                .contentType("application/json"),
        )

    fun authPostRequest(
        url: String,
        body: Any? = null,
        pathParams: Any? = null,
    ): ResultActions =
        mockMvc.perform(
            post(url, pathParams)
                .header("Authorization", "Bearer ${token.accessToken}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(body)),
        )

    fun authPatchRequest(
        url: String,
        body: Any,
        pathParams: Any? = null,
    ): ResultActions =
        mockMvc.perform(
            patch(url, pathParams)
                .header("Authorization", "Bearer ${token.accessToken}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(body)),
        )

    fun authPutRequest(
        url: String,
        body: Any,
        pathParams: Any? = null,
    ): ResultActions =
        mockMvc.perform(
            put(url, pathParams)
                .header("Authorization", "Bearer ${token.accessToken}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(body)),
        )

    fun authDeleteRequest(
        url: String,
        pathParams: Any? = null,
    ): ResultActions =
        mockMvc.perform(
            delete(url, pathParams)
                .header("Authorization", "Bearer ${token.accessToken}")
                .contentType("application/json"),
        )
}
