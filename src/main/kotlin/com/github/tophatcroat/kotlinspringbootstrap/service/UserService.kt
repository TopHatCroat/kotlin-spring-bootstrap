package com.github.tophatcroat.kotlinspringbootstrap.service

import com.github.tophatcroat.kotlinspringbootstrap.exception.CredentialsException
import com.github.tophatcroat.kotlinspringbootstrap.model.User
import com.github.tophatcroat.kotlinspringbootstrap.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class UserService(val repository: UserRepository,
                  @Value("\${jwt.secret}") val jwtSecret: String,
                  @Value("\${jwt.issuer}") val jwtIssuer: String) {

    private val user = ThreadLocal<User>()

    fun findByToken(token: String) = repository.findByToken(token)

    fun getCurrentUser() = user.get()

    fun setCurrentUser(user: User?) {
        this.user.set(user)
    }

    @Throws(CredentialsException::class)
    fun login(email: String, password: String): User {
        val user = repository.findByEmail(email)

        if (BCrypt.checkpw(password, user?.password).not())
            throw CredentialsException()

        user?.token = newToken(user!!)

        return user
    }

    fun newToken(user: User) = Jwts.builder()
            .setIssuedAt(Date())
            .setSubject(user.email)
            .setIssuer(jwtIssuer)
            .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact()!!

    fun register(name: String, password: String) {
        val hash = BCrypt.hashpw(password, BCrypt.gensalt())
        repository.saveAndFlush(User(null, name, hash))
    }
}