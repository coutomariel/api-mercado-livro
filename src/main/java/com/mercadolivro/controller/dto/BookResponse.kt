package com.mercadolivro.controller.dto

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class BookResponse(
    val id: Int,
    val name: String,
    val price: BigDecimal,
    val status: BookStatus,
) {

    companion object {
        fun fromModel(book: BookModel): BookResponse {
            return BookResponse(
                id = book.id!!,
                name = book.name,
                price = book.price,
                status = book.status!!
            )

        }
    }

}
