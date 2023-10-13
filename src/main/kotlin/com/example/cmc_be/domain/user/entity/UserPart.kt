package com.example.cmc_be.domain.user.entity

import com.example.cmc_be.common.dto.BaseEntity
import com.example.cmc_be.domain.user.enums.Generation
import com.example.cmc_be.domain.user.enums.Part
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@Table(name = "UserPart")
@DynamicUpdate
@BatchSize(size = 100)
@DynamicInsert
class UserPart : BaseEntity() {
    @Id
    @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private val user: User? = null


    @Enumerated(EnumType.STRING) val part : Part? = null

    @Enumerated(EnumType.STRING) val generation : Generation? = null

}