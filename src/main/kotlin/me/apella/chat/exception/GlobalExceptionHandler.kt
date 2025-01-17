package me.apella.chat.exception

import jakarta.persistence.EntityNotFoundException
import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {
    private fun handleException(
        exception: Exception,
        status: HttpStatus,
    ): ResponseEntity<CustomException> {
        val customException = CustomException(
            timestamp = LocalDateTime.now(),
            status = status,
            message = exception.message ?: "An error occurred!"
        )
        return ResponseEntity(customException, status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<CustomException> =
        handleException(exception, status = HttpStatus.BAD_REQUEST)

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<CustomException> =
        handleException(exception, status = HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(exception: EntityNotFoundException): ResponseEntity<CustomException> =
        handleException(exception, status = HttpStatus.NOT_FOUND)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<CustomException> = handleException(exception, status = HttpStatus.BAD_REQUEST)

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        exception: MethodArgumentTypeMismatchException
    ): ResponseEntity<CustomException> = handleException(exception, status = HttpStatus.BAD_REQUEST)

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        exception: ConstraintViolationException
    ): ResponseEntity<CustomException> = handleException(exception, status = HttpStatus.BAD_REQUEST)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        exception: HttpMessageNotReadableException
    ): ResponseEntity<CustomException> = handleException(exception, status = HttpStatus.BAD_REQUEST)

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(
        exception: DataIntegrityViolationException
    ): ResponseEntity<CustomException> = handleException(exception, HttpStatus.CONFLICT)

    @ExceptionHandler(OptimisticLockingFailureException::class)
    fun handleOptimisticLockingFailureException(
        exception: OptimisticLockingFailureException
    ): ResponseEntity<CustomException> = handleException(exception, HttpStatus.CONFLICT)

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(exception: AccessDeniedException): ResponseEntity<CustomException> =
        handleException(exception, status = HttpStatus.FORBIDDEN)

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(exception: AuthenticationException): ResponseEntity<CustomException> =
        handleException(exception, status = HttpStatus.UNAUTHORIZED)
}
