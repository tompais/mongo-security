package com.example.mongo_security.security.requests

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

@Schema(description = "SignInRequest")
data class SignInRequest(
    @field:NotBlank val username: String,
    @field:NotBlank val password: String
)
