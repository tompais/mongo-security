package com.example.mongo_security.config

import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Base64Config {
    @Bean
    fun base64Codec(): Base64 = Base64()
}
