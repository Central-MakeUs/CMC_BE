package com.example.cmc_be.domain.notification.entity

import com.example.cmc_be.common.dto.BaseEntity
import com.example.cmc_be.domain.user.enums.Generation
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

@Entity
@Table(name = "Notification")
@DynamicUpdate
@BatchSize(size = 100)
@DynamicInsert
data class Notification(
    @Id
    @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    @Enumerated(EnumType.STRING) val generation : Generation,
    val title : String,
    val notionUrl : String
) : BaseEntity(){

}