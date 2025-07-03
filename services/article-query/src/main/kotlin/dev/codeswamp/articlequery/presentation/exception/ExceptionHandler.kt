package dev.codeswamp.articlequery.presentation.exception

import dev.codeswamp.core.common.exception.BadRequestException
import dev.codeswamp.core.common.exception.BaseException
import dev.codeswamp.core.common.exception.ConflictException
import dev.codeswamp.core.common.exception.ForbiddenException
import dev.codeswamp.core.common.exception.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["dev.codeswamp.core.article.presentation.controller"])
class ExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(BaseException::class)
    fun handleException(exception: BaseException): ResponseEntity<ErrorResponse> {
        logger.error(exception.message)

        val httpStatus = when (exception) {
            is NotFoundException-> HttpStatus.NOT_FOUND
            is BadRequestException-> HttpStatus.BAD_REQUEST
            is ConflictException-> HttpStatus.CONFLICT
            is ForbiddenException-> HttpStatus.FORBIDDEN
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        return ResponseEntity.status(httpStatus).body(ErrorResponse.from(exception))
    }
}