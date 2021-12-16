package com.example.mongo_security.security.requests

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignUpRequest(
    @field:NotBlank val firstname: String,
    @field:NotBlank val lastname: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank val username: String,
    @field:NotBlank val password: String
)
