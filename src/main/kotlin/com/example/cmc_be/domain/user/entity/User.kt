package com.example.cmc_be.domain.user.entity

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "User")
@DynamicUpdate
@BatchSize(size = 100)
@DynamicInsert
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "username")
    private val username: String,
    @Column(name = "password")
    private val password: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "nickname")
    val nickname: String,
    @Column(name = "phoneNumber")
    val phoneNumber: String,
    @Column(name = "role")
    val role: String
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return role.split(",").map { SimpleGrantedAuthority(it) }
    }

    override fun getPassword(): String = password
    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return false
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return false
    }

    fun isActivated(): Boolean {
        return true
    }
}
