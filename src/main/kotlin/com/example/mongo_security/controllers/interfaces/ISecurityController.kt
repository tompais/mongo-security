package com.example.mongo_security.controllers.interfaces

import com.example.mongo_security.security.requests.SignUpRequest
import com.example.mongo_security.security.responses.SignUpResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid

@RequestMapping("/security")
interface ISecurityController {
    @ResponseStatus(CREATED)
    @PostMapping("/sign-up")
    fun signUp(@RequestBody @Valid signUpRequest: SignUpRequest): SignUpResponse
}
