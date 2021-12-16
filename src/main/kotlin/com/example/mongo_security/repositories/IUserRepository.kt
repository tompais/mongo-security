package com.example.mongo_security.repositories

import com.example.mongo_security.models.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IUserRepository : MongoRepository<User, String> {
    fun findFirstByUsername(username: String): User?
}
