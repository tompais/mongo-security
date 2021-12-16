package com.example.mongo_security.services.implementations

import com.example.mongo_security.models.User
import com.example.mongo_security.security.requests.SignUpRequest
import com.example.mongo_security.security.responses.SignUpResponse
import com.example.mongo_security.services.interfaces.ISecurityService
import com.example.mongo_security.services.interfaces.IUserService
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SecurityService(
    private val userService: IUserService,
    private val passwordEncoder: PasswordEncoder
) : ISecurityService {
    private fun createUser(signUpRequest: SignUpRequest): User {
        val username = signUpRequest.username
        val email = signUpRequest.email

        return try {
            userService.createUser(
                User(
                    firstname = signUpRequest.firstname,
                    lastname = signUpRequest.lastname,
                    email = email,
                    username = username,
                    password = passwordEncoder.encode(signUpRequest.password)
                )
            )
        } catch (dke: DuplicateKeyException) {
            throw ResponseStatusException(
                BAD_REQUEST,
                "The user [$username] with email [$email] already exists.",
                dke
            )
        }
    }

    private fun buildSignUpResponse(createdUser: User): SignUpResponse = SignUpResponse(
        id = createdUser.id,
        firstname = createdUser.firstname,
        lastname = createdUser.lastname,
        email = createdUser.email,
        username = createdUser.username
    )

    override fun signUp(signUpRequest: SignUpRequest): SignUpResponse = buildSignUpResponse(createUser(signUpRequest))
}
