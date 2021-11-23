package com.mercadolivro.controller.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateBookRequest(
    @field:NotEmpty val name: String,
    @field:NotNull val price: BigDecimal,
    @JsonAlias("customer_id")
    @field:NotNull val customerId: String
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
