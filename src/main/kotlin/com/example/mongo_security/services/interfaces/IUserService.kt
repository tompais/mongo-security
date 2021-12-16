package com.example.mongo_security.services.interfaces

import com.example.mongo_security.models.User
import org.springframework.security.core.userdetails.UserDetailsService

interface IUserService : UserDetailsService {
    fun createUser(user: User): User
}
