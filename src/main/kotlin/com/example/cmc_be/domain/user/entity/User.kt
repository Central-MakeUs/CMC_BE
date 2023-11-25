package com.example.cmc_be.domain.user.entity

import com.example.cmc_be.common.dto.BaseEntity
import com.example.cmc_be.domain.user.enums.SignUpApprove
import com.example.cmc_be.domain.user.enums.UserRole
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
    val id: Long = 0L,
    @Column(name = "username")
    private val username: String,
    @Column(name = "password")
    private var password: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "nickname")
    val nickname: String,
    val role: String = UserRole.ROLE_USER.value,
    val nowGeneration: Int,
    @Enumerated(EnumType.STRING) val signUpApprove: SignUpApprove = SignUpApprove.NOT,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    private val userPart: List<UserPart> = ArrayList<UserPart>()
) : UserDetails, BaseEntity() {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return role.split(",").map { SimpleGrantedAuthority(it) }
    }

    override fun getPassword(): String {
        return password
    }

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

    fun changeUserPassword(user: User, newPassword: String): User {
        return user.copy(password = newPassword)
    }

    fun modifyPassword(password: String) {
        this.password = password
    }
}
