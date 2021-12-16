package com.example.mongo_security.services.implementations

import com.example.mongo_security.models.User
import com.example.mongo_security.repositories.IUserRepository
import com.example.mongo_security.services.interfaces.IUserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: IUserRepository
) : IUserService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may be case-sensitive, or case-insensitive depending on how the
     * implementation instance is configured. In this case, the `UserDetails`
     * object that comes back may have a username that is of a different case than what
     * was actually requested…
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never `null`)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    override fun loadUserByUsername(username: String): UserDetails = userRepository.findFirstByUsername(username)
        ?: throw UsernameNotFoundException("The user [$username] was not found.")

    override fun createUser(user: User): User = userRepository.save(user)
}
