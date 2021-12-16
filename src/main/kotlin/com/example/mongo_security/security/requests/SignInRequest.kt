package com.example.mongo_security.security.requests

import javax.validation.constraints.NotBlank

data class SignInRequest(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String
)
