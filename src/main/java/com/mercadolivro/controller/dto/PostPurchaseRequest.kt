package com.mercadolivro.controller.dto

import com.mercadolivro.validation.ValidUUID

data class  PostPurchaseRequest(
    @field:ValidUUID val customerId: String,
    val books : Set<@ValidUUID String>

)
