package me.apella.chat.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import me.apella.chat.dto.ChatResponse
import me.apella.chat.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chats")
@Tag(name = "Chats", description = "Endpoints related to the system chat.")
class ChatController(val chatService: ChatService) {
    @Operation(
        summary = "Get user's chats",
        description = "Retrieves a list of chats for the authenticated user."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Chats retrieved successfully!"),
        ApiResponse(responseCode = "401", description = "Unauthorized!"),
        ApiResponse(responseCode = "500", description = "Internal server error!"),
    ])
    @GetMapping
    fun getChatsByReceiver(authentication: Authentication): ResponseEntity<List<ChatResponse>> {
        return ResponseEntity.ok(chatService.getChatsBySenderId(authentication))
    }
}