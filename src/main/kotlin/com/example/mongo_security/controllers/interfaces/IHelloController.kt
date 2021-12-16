package com.example.mongo_security.controllers.interfaces

import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/hello", produces = [TEXT_PLAIN_VALUE])
interface IHelloController {
    @GetMapping("/world")
    fun helloWorld(): String

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    fun helloAdmin(): String
}
