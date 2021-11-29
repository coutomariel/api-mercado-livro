package com.mercadolivro.exception.advice

import com.mercadolivro.exception.BookNotFoundException
import com.mercadolivro.exception.BookSoldException
import com.mercadolivro.exception.CustomerNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException,
        request: WebRequest,
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            httpCode = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            message = ErrorType.ML001.message,
            internalCode = ErrorType.ML001.code,
            errorFields = exception.bindingResult.fieldErrors.map { error ->
                ErrorField(
                    message = error.defaultMessage ?: "Invalid",
                    field = error.field)
            }

        )
        return ResponseEntity.unprocessableEntity().body(errorDetails)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        exception: IllegalArgumentException,
        request: WebRequest,
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            httpCode = HttpStatus.BAD_REQUEST.value(),
            message = ErrorType.ML002.message,
            internalCode = ErrorType.ML002.code,
            errorFields = null
        )
        return ResponseEntity.unprocessableEntity().body(errorDetails)
    }

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

    @ExceptionHandler(BookSoldException::class)
    fun handleBookSoldException(
        exception: BookSoldException,
        request: WebRequest,
    ): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(
            httpCode = HttpStatus.UNPROCESSABLE_ENTITY.value(),
            message = exception.message,
            internalCode = exception.internalCode,
            errorFields = null
        )
        return ResponseEntity.unprocessableEntity().body(errorDetails)
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