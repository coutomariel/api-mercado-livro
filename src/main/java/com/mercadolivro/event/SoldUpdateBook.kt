package com.mercadolivro.event

import com.mercadolivro.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class SoldUpdateBook(
    private val bookService: BookService
) {

    @EventListener
    private fun soldUpdateBookListener(purchaseEvent: PurchaseEvent) {
        bookService.soldBook(purchaseEvent.purchaseModel.books)

    }
}