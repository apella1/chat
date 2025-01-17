package me.apella.chat.controller

import io.swagger.v3.oas.annotations.tags.Tag
import me.apella.chat.dto.ChatResponse
import me.apella.chat.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chats")
@Tag(name = "Chats", description = "Endpoints related to the system chat.")
class ChatController(val chatService: ChatService) {
    @GetMapping
    fun getChatsByReceiver(authentication: Authentication): ResponseEntity<List<ChatResponse>> {
        return ResponseEntity.ok(chatService.getChatsByReceiverId(authentication))
    }
}