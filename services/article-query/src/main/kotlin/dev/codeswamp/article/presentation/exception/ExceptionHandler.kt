package dev.codeswamp.article.presentation.exception

import dev.codeswamp.article.application.exception.application.*
import dev.codeswamp.article.domain.exception.domain.*
import dev.codeswamp.article.infrastructure.exception.infrastructure.InfraConflictException
import dev.codeswamp.article.infrastructure.exception.infrastructure.InfraException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["dev.codeswamp.core.article.presentation.controller"])
class ExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(exception: DomainException): ResponseEntity<ErrorResponse> {
        logger.error(exception.message)

        val httpStatus = when (exception) {
            is DomainNotFoundException -> HttpStatus.NOT_FOUND
            is DomainBadRequestException -> HttpStatus.BAD_REQUEST
            is DomainConflictException -> HttpStatus.CONFLICT
            is DomainForbiddenException -> HttpStatus.FORBIDDEN
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        return ResponseEntity.status(httpStatus).body(ErrorResponse.from(exception))
    }

    @ExceptionHandler(AppException::class)
    fun handleAppException(exception: AppException): ResponseEntity<ErrorResponse> {
        logger.error(exception.message)

        val httpStatus = when (exception) {
            is AppNotFoundException -> HttpStatus.NOT_FOUND
            is AppBadRequestException -> HttpStatus.BAD_REQUEST
            is AppConflictException -> HttpStatus.CONFLICT
            is AppForbiddenException -> HttpStatus.FORBIDDEN
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        return ResponseEntity.status(httpStatus).body(ErrorResponse.from(exception))
    }

    @ExceptionHandler(InfraException::class)
    fun handleInfraException(exception: InfraException): ResponseEntity<ErrorResponse> {
        logger.error(exception.message)

        val httpStatus = when (exception) {
            is InfraConflictException -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        return ResponseEntity.status(httpStatus).body(ErrorResponse.from(exception))
    }
}