package com.github.tophatcroat.kotlinspringbootstrap.domain.model

import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "users")
class User : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    val id: Long? = null

    var email: String = ""

    var passwd: String = ""

    var role: String = "USER"

    var createdAt: Instant = Instant.now()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            mutableListOf(SimpleGrantedAuthority(role))

    override fun getUsername() = email

    override fun getPassword() = passwd

    override fun isEnabled() = true

    override fun isCredentialsNonExpired() = true

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}
