package com.example.mongo_security.controllers.implementations

import com.example.mongo_security.controllers.interfaces.IPingController
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController : IPingController {
    override fun ping(): String = "pong"
}
