package com.mercadolivro.exception.advice

enum class ErrorType(val message: String, val code: String) {
    ML001("Invalid request", "ML-001"),
    ML002("Invalid parameter argument", "ML-002"),
    ML101("Not exists cutomer with ID: [%s]", "ML-101"),
    ML201("Not exists book with ID: [%s]", "ML-201");
}
