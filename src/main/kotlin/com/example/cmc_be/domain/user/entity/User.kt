package com.example.cmc_be.domain.user.entity

import jakarta.persistence.*
import com.example.cmc_be.common.dto.BaseEntity
import com.example.cmc_be.domain.user.enums.Generation
import com.example.cmc_be.domain.user.enums.SignUpApprove
import com.example.cmc_be.domain.user.enums.UserRole
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
class User : UserDetails, BaseEntity()  {
    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null
    //유저 아이디
    private val username: String? = null

    private val password: String? = null

    @Column(name = "name") val name: String? = null

    val nickname: String? = null

    @Column(name = "phoneNumber")
    val phoneNumber: String? = null

    @Enumerated(EnumType.STRING) val role : UserRole = UserRole.ROLE_USER

    @Enumerated(EnumType.STRING) val  nowGeneration : Generation? = null


    @Enumerated(EnumType.STRING) val  signUpApprove : SignUpApprove = SignUpApprove.NOT

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId")
    private val userCard: List<UserPart> = ArrayList<UserPart>()
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
