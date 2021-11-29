package com.mercadolivro.controller.mapper

import com.mercadolivro.controller.dto.PostPurchaseRequest
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val customerService: CustomerService,
    private val bookService: BookService,
) {

    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val books = bookService.getAllIds(request.books)
        return PurchaseModel(
            customerModel = customerService.getById(request.customerId),
            books = books,
            price = books.sumOf { it.price }, //.map { book -> book.price }.reduce { acc, value -> acc + value },
            nfe = "nfe"
        )
    }


}
