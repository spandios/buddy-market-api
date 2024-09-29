package com.alpaca.buddymarket.user

import org.springframework.web.bind.annotation.RestController

@RestController("/v1/user")
class UserController(
    private val userService: UserService,
)
