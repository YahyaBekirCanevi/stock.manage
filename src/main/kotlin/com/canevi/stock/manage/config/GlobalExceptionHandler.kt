package com.canevi.stock.manage.config

import com.canevi.stock.manage.config.exception.CouldNotSaveException
import com.canevi.stock.manage.config.exception.ResourceNotFoundException
import com.canevi.stock.manage.web.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    // Handle custom ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: ResourceNotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "Resource not found",
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }
    // Handle custom CouldNotSaveException
    @ExceptionHandler(CouldNotSaveException::class)
    fun handleCouldNotSaveException(
        ex: CouldNotSaveException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.EXPECTATION_FAILED.value(),
            message = ex.message ?: "Error happened while saving",
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.EXPECTATION_FAILED)
    }
    // Handle generic exceptions
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "An error occurred: ${ex.localizedMessage}",
            timestamp = System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}