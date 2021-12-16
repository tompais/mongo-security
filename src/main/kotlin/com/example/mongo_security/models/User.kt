package com.example.mongo_security.models

import com.example.mongo_security.models.User.Role.NORMAL
import com.example.mongo_security.models.User.Status.ACTIVE
import com.fasterxml.jackson.annotation.JsonValue
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Document("users")
data class User(
    @field:NotBlank val firstname: String,
    @field:NotBlank val lastname: String,
    @field:NotBlank @field:Email @Indexed(unique = true) val email: String,
    @field:NotBlank @Indexed(unique = true) private val username: String,
    @field:NotBlank private val password: String,
    val role: Role = NORMAL,
    val status: Status = ACTIVE,
    @CreatedDate val creationDate: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate val lastUpdate: LocalDateTime = LocalDateTime.now()
) : UserDetails {
    @Id
    lateinit var id: String

    /**
     * Returns the authorities granted to the user. Cannot return `null`.
     * @return the authorities, sorted by natural key (never `null`)
     */
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(
        SimpleGrantedAuthority(role.toString())
    )

    override fun getPassword(): String = password

    /**
     * Returns the username used to authenticate the user. Cannot return
     * `null`.
     * @return the username (never `null`)
     */
    override fun getUsername(): String = username

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     * @return `true` if the user's account is valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isAccountNonExpired(): Boolean = true

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     * @return `true` if the user is not locked, `false` otherwise
     */
    override fun isAccountNonLocked(): Boolean = true

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     * @return `true` if the user's credentials are valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isCredentialsNonExpired(): Boolean = true

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     * @return `true` if the user is enabled, `false` otherwise
     */
    override fun isEnabled(): Boolean = status == ACTIVE

    enum class Role {
        NORMAL,
        ADMIN;

        @JsonValue
        override fun toString(): String = name.lowercase()
    }

    enum class Status {
        ACTIVE,
        INACTIVE;

        @JsonValue
        override fun toString(): String = name.lowercase()
    }
}
