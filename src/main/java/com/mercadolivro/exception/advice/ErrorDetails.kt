package com.mercadolivro.exception.advice

import org.springframework.http.HttpStatus

data class ErrorDetails(
    val httpCode: Int,
    val message: String,
    val internalCode: String,
    var errorFields: List<ErrorField>?
)