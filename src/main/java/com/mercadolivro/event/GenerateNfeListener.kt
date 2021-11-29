package com.mercadolivro.event

import com.mercadolivro.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class GenerateNfeListener(
    private val service: PurchaseService,
) {

    @Async
    @EventListener
    private fun generateNfe(purchaseEvent: PurchaseEvent) {
        val nfe = UUID.randomUUID().toString()
        val purchase = purchaseEvent.purchaseModel.copy(nfe = nfe)
        service.update(purchase)
    }
}