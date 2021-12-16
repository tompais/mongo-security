package com.example.mongo_security.security.filters

import com.example.mongo_security.security.requests.SignInRequest
import com.example.mongo_security.security.responses.SignInResponse
import com.example.mongo_security.security.utils.SecurityConstant
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS512
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import java.io.IOException
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Validator

@Component
class JwtAuthenticationFilter(
    @Lazy authenticationManager: AuthenticationManager,
    private val objectMapper: ObjectMapper,
    private val validator: Validator,
    @Value("\${security.jwt.secret-key}") private val secretKey: String
) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    init {
        this.setFilterProcessesUrl("/security/sign-in")
    }

    private companion object {
        private const val JWT_ISSUER = "example.io"
    }

    private fun HttpServletRequest.getSignInRequest(): SignInRequest = try {
        objectMapper.readValue(inputStream)
    } catch (e: IOException) {
        throw ResponseStatusException(BAD_REQUEST, "Invalid sign in request", e)
    }

    private fun validateSignInRequest(signInRequest: SignInRequest) {
        validator.validate(signInRequest).also { constraintViolations ->
            if (constraintViolations.isNotEmpty()) {
                throw ResponseStatusException(
                    BAD_REQUEST,
                    constraintViolations.joinToString { "${it.propertyPath}: ${it.message}" }
                )
            }
        }
    }

    private fun HttpServletRequest.getAndValidateSignInRequest(): SignInRequest =
        getSignInRequest().also(::validateSignInRequest)

    private fun buildUsernamePasswordAuthenticationToken(signInRequest: SignInRequest): UsernamePasswordAuthenticationToken =
        UsernamePasswordAuthenticationToken(
            signInRequest.username,
            signInRequest.password,
            emptyList()
        )

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication =
        authenticationManager.authenticate(
            buildUsernamePasswordAuthenticationToken(request.getAndValidateSignInRequest())
        )

    private fun generateToken(user: UserDetails): String = Jwts.builder()
        .setSubject(user.username)
        .claim(SecurityConstant.ClaimKey.AUTHORITY, user.authorities.first().authority)
        .setIssuer(JWT_ISSUER)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
        .signWith(Keys.hmacShaKeyFor(secretKey.encodeToByteArray()), HS512)
        .compact()

    private fun HttpServletResponse.sendSignInResponse(token: String) {
        apply {
            contentType = APPLICATION_JSON_VALUE
            outputStream.print(objectMapper.writeValueAsString(SignInResponse(token)))
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        generateToken(authResult.principal as UserDetails).also { token ->
            response.sendSignInResponse(token)
        }
    }
}
