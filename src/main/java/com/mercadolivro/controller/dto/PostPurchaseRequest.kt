package com.mercadolivro.controller.dto

data class  PostPurchaseRequest(
    val customerId: Int,
    val books : Set<Int>

)
