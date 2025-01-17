package me.apella.chat.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import me.apella.chat.enums.MessageState
import me.apella.chat.enums.MessageType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener::class)
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    val content: String,
    @Enumerated(EnumType.STRING)
    val state: MessageState,
    @Enumerated(EnumType.STRING)
    val type: MessageType,
    @ManyToOne
    @JoinColumn(name = "chat_id")
    val chat: Chat,
    @Column(name = "sender_id", nullable = false)
    val senderId: UUID,
    @Column(name = "receiver_id", nullable = false)
    val receiverId: UUID,
    val mediaFilePath: String?,
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: LocalDateTime,
    @LastModifiedDate
    @Column(name = "last_modified", insertable = false, updatable = true)
    val lastModifiedDate: LocalDateTime
)