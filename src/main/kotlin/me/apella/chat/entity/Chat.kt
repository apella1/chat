package me.apella.chat.entity

import jakarta.persistence.*
import me.apella.chat.enums.MessageState
import me.apella.chat.enums.MessageType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "chats")
@EntityListeners(AuditingEntityListener::class)
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    @ManyToOne
    @JoinColumn(name = "sender_id")
    val sender: User,
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    val recipient: User,
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    @OrderBy("createdAt DESC")
    val messages: List<Message>,
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: LocalDateTime,
    @LastModifiedDate
    @Column(name = "last_modified", insertable = false)
    val lastModifiedDate: LocalDateTime
) {
    @Transient
    fun getChatName(senderId: String): String {
        if (recipient.id.toString() == senderId) {
            return "${sender.firstName} ${sender.lastName}"
        }

        return "${recipient.firstName} ${recipient.lastName}"
    }

    @Transient
    fun getUnreadMessages(userId: UUID): Int {
        return messages.filter { message ->
            userId == message.receiverId && message.state == MessageState.SENT
        }.size
    }

    @Transient
    fun getLastMessage(): String? {
        if (messages.isNotEmpty()) {
            return if (messages.first().type == MessageType.TEXT) {
                messages.first().content
            } else {
                "Attachment"
            }
        }
        return null
    }

    @Transient
    fun getLastMessageTime(): LocalDateTime? {
        return if (messages.isNotEmpty()) {
            messages.first().createdAt
        } else {
            null
        }
    }
}