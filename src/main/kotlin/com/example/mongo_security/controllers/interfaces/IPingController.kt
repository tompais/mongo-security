package com.example.mongo_security.controllers.interfaces

import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.web.bind.annotation.GetMapping

interface IPingController {
    @GetMapping("/ping", produces = [TEXT_PLAIN_VALUE])
    fun ping(): String
}
