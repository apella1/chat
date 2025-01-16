package me.apella.chat.repository

import me.apella.chat.entity.Message
import me.apella.chat.enums.MessageState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface MessageRepository : JpaRepository<Message, UUID> {
    fun findMessagesByChatId(chatId: UUID): List<Message>

    @Modifying
    @Query("UPDATE Message SET state =: newState WHERE chat.id =: chatId")
    fun setMessagesToSeenByChatId(@Param("chatId") chatId: UUID, @Param("newState") state: MessageState)
}