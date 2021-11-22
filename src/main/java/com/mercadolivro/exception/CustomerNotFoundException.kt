package com.mercadolivro.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class CustomerNotFoundException(override val message: String, val internalCode: String) : Exception()
