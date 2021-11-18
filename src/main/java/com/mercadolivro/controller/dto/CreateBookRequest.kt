package com.mercadolivro.controller.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import java.math.BigDecimal

data class CreateBookRequest(
    val name: String,
    val price: BigDecimal,
    @JsonAlias("customer_id")
    val customerId: String
) {
    fun toModel(customer: CustomerModel): BookModel {
        return BookModel(
            name = name,
            price = price,
            status = BookStatus.ATIVO,
            customer = customer
        )
    }
}
