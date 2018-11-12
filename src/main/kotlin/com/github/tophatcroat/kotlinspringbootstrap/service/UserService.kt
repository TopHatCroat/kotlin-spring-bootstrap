package com.github.tophatcroat.kotlinspringbootstrap.service

import com.github.tophatcroat.kotlinspringbootstrap.domain.UserRepository
import com.github.tophatcroat.kotlinspringbootstrap.domain.model.User
import com.github.tophatcroat.kotlinspringbootstrap.exception.CredentialsException
import org.mindrot.jbcrypt.BCrypt
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.Instant
import javax.transaction.Transactional

@Service
@Transactional
class UserService(val repository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? =
            repository.findByEmail(username) ?: throw CredentialsException()

    fun register(username: String, password: String): User {
        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

        return repository.saveAndFlush(
                User().apply {
                    email = username
                    passwd = hashedPassword
                    role = "USER"
                    createdAt = Instant.now()
                })
    }

    fun registerWithOauth(username: String, token: String): User =
            repository.saveAndFlush(User().apply {
                email = username
                role = "USER"
                createdAt = Instant.now()
            })
}
