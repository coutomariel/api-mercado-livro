package com.mercadolivro.controller.dto

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateBookRequest(
    @field:NotEmpty val name: String,
    @field:NotNull val price: BigDecimal,
) {
    fun toModel(): BookModel {
        return BookModel(
            name = name,
            price = price,
            status = BookStatus.ATIVO,
        )
    }
}
