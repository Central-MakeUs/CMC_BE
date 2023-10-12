package com.example.cmc_be.domain.user.entity

import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "`User`")
@DynamicUpdate
@BatchSize(size = 100)
@DynamicInsert
class User : UserDetails {
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null
    //유저 아이디
    private val username: String? = null

    private val password: String? = null

    @Column(name = "name") val name: String? = null

    val nickname: String? = null

    @Column(name = "phoneNumber") val phoneNumber: String? = null

    @Column(name = "role") val role: String? = null


    override fun getAuthorities(): Collection<GrantedAuthority>? {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        for (role in role!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) authorities.add(
            SimpleGrantedAuthority(role)
        )
        return authorities
    }


    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }

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