package com.mercadolivro.config.security

class AuthenticationException(override val message: String, val internalCode: String) : Exception()
