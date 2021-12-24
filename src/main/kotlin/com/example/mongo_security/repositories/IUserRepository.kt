package com.example.mongo_security.repositories

import com.example.mongo_security.config.OpenAPIConfig.Companion.BEARER_KEY
import com.example.mongo_security.models.User
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
@SecurityRequirements(SecurityRequirement(name = BEARER_KEY))
interface IUserRepository : MongoRepository<User, String> {
    fun findFirstByUsername(username: String): User?
}
