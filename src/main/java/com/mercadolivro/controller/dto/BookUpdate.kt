package com.mercadolivro.controller.dto

import java.math.BigDecimal

data class BookUpdate(
    val name : String?,
    val price: BigDecimal?
)
