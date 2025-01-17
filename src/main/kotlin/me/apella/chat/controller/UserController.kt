package me.apella.chat.controller

import io.swagger.v3.oas.annotations.tags.Tag
import me.apella.chat.dto.UserResponse
import me.apella.chat.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints related to the system users.")
class UserController (val userService: UserService) {
    @GetMapping
    fun getAllUsers(authentication: Authentication): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.findAllUsersExceptSelf(authentication))
    }
}