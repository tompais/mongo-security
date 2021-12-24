package com.example.mongo_security.controllers.interfaces

import com.example.mongo_security.config.OpenAPIConfig.Companion.BEARER_KEY
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/hello", produces = [TEXT_PLAIN_VALUE])
interface IHelloController {
    @GetMapping("/world")
    @Operation(security = [SecurityRequirement(name = BEARER_KEY)])
    fun helloWorld(): String

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    @Operation(security = [SecurityRequirement(name = BEARER_KEY)])
    fun helloAdmin(): String
}
