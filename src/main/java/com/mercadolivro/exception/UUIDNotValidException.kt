package com.mercadolivro.exception

class UUIDNotValidException(override val message: String, val internalCode: String) : Exception()
