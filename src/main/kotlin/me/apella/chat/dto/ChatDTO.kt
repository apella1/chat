package me.apella.chat.dto

import java.time.LocalDateTime

data class ChatResponse(
    val id: String,
    val name: String,
    val unreadCount: Long,
    val lastMessage: String,
    val lastMessageTime: LocalDateTime?,
    val isRecipientOnline: Boolean,
    val senderId: String,
    val receiverId: String
)