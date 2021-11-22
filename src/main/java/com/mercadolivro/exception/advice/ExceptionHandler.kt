package com.mercadolivro.exception.advice

import com.mercadolivro.exception.BookNotFoundException
import com.mercadolivro.exception.CustomerNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException::class)
    fun handleCustomerNotFounException(
        exception: CustomerNotFoundException,
        request: WebRequest,
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            httpCode = HttpStatus.BAD_REQUEST.value(),
            message = exception.message,
            internalCode = exception.internalCode,
            errorFields = null
        )
        return ResponseEntity.badRequest().body(errorDetails)
    }

    @ExceptionHandler(BookNotFoundException::class)
    fun handleBookNotFounException(
        exception: BookNotFoundException,
        request: WebRequest,
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            httpCode = HttpStatus.BAD_REQUEST.value(),
            message = exception.message,
            internalCode = exception.internalCode,
            errorFields = null
        )
        return ResponseEntity.badRequest().body(errorDetails)
    }

}