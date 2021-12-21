package com.mercadolivro.event

import com.mercadolivro.service.BookService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SoldUpdateBook(
    private val bookService: BookService
) {

    @Async
    @EventListener
    fun soldUpdateBookListener(purchaseEvent: PurchaseEvent) {
        bookService.soldBook(purchaseEvent.purchaseModel.books)

    }
}