package com.mercadolivro.exception

class BookSoldException(override val message: String, val internalCode: String): Exception()
