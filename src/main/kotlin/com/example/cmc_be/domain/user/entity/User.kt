package com.example.cmc_be.domain.user.entity

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
import javax.persistence.*

@Entity
@Table(name = "`User`")
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

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        val authorities: MutableCollection<GrantedAuthority> = ArrayList()
        for (role in role.value!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) authorities.add(
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