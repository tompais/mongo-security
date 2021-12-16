package com.example.mongo_security.services.interfaces

import com.example.mongo_security.security.requests.SignUpRequest
import com.example.mongo_security.security.responses.SignUpResponse

interface ISecurityService {
    fun signUp(signUpRequest: SignUpRequest): SignUpResponse
}
