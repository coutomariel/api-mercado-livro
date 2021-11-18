package com.mercadolivro.controller.dto

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import java.math.BigDecimal
import java.util.*

class BookResponse(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val status: BookStatus,
    val customerId: UUID
) {

    companion object {
        fun fromModel(book: BookModel): BookResponse {
            return BookResponse(
                id = book.id,
                name = book.name,
                price = book.price,
                status = book.status!!,
                customerId = book.customer!!.customerId,
            )

        }
    }

}
