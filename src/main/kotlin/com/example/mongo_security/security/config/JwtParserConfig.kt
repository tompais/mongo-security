package com.example.mongo_security.security.config

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtParserConfig {
    @Bean
    fun jwtParser(
        base64Codec: Base64,
        @Value("\${security.jwt.secret-key}") secretKey: String
    ): JwtParser = Jwts.parserBuilder()
        .setSigningKey(base64Codec.encodeAsString(secretKey.encodeToByteArray()))
        .build()
}
