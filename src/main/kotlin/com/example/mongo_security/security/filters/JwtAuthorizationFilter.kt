package com.example.mongo_security.security.filters

import com.example.mongo_security.security.utils.SecurityConstant
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(@Lazy authenticationManager: AuthenticationManager, private val jwtParser: JwtParser) :
    BasicAuthenticationFilter(authenticationManager) {
    private companion object {
        private const val JWT_PREFIX = "Bearer "
    }

    private fun isValidAuthorizationHeader(authorizationHeader: String): Boolean =
        authorizationHeader.isNotBlank() && authorizationHeader.startsWith(JWT_PREFIX)

    private fun HttpServletRequest.getTokenFromAuthorizationHeader(): String? =
        getHeader(AUTHORIZATION)?.let { authorizationHeader ->
            if (isValidAuthorizationHeader(authorizationHeader)) {
                authorizationHeader.removePrefix(JWT_PREFIX)
            } else {
                null
            }
        }

    private fun getClaims(token: String): Claims = jwtParser.parseClaimsJws(token).body

    fun Claims.getAuthority(): GrantedAuthority = SimpleGrantedAuthority(
        this[SecurityConstant.ClaimKey.AUTHORITY].toString()
    )

    private fun buildUsernamePasswordAuthenticationToken(claims: Claims): UsernamePasswordAuthenticationToken =
        UsernamePasswordAuthenticationToken(
            claims,
            null,
            mutableListOf(
                claims.getAuthority()
            )
        )

    private fun setAuthenticationInSecurityContext(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun buildAuthenticationAndSetItInSecurityContext(claims: Claims) {
        buildUsernamePasswordAuthenticationToken(claims).also(::setAuthenticationInSecurityContext)
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        request.getTokenFromAuthorizationHeader()?.also { token ->
            try {
                getClaims(token).also(::buildAuthenticationAndSetItInSecurityContext)
            } catch (e: Exception) {
                throw AccessDeniedException("Error validating access token", e)
            }
        }
        chain.doFilter(request, response)
    }
}
