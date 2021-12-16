package com.example.mongo_security.controllers.implementations

import com.example.mongo_security.controllers.interfaces.ISecurityController
import com.example.mongo_security.security.requests.SignUpRequest
import com.example.mongo_security.security.responses.SignUpResponse
import com.example.mongo_security.services.interfaces.ISecurityService
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityController(
    private val securityService: ISecurityService
) : ISecurityController {
    override fun signUp(signUpRequest: SignUpRequest): SignUpResponse = securityService.signUp(signUpRequest)
}
