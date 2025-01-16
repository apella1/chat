package me.apella.chat.repository

import me.apella.chat.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findUserByEmail(@Param("email") email: String): User?
    @Query("select u from User u where u.id != :currentUserId")
    fun findAllUsersExceptSelf(currentUserId: UUID): List<User>
    @Query("select u from User u where u.id = :publicId")
    fun findUserByPublicId(@Param("publicId") publicId: UUID)
}