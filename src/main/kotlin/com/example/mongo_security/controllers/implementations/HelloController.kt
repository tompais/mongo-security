package com.example.mongo_security.controllers.implementations

import com.example.mongo_security.controllers.interfaces.IHelloController
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController : IHelloController {
    override fun helloWorld(): String = "Hello World!"
    override fun helloAdmin(): String = "Hello Admin!"
}
