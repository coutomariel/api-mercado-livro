package com.mercadolivro.event

import com.mercadolivro.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class GenerateNfeListener(
    private val service: PurchaseService,
) {

    @EventListener
    private fun generateNfe(purchaseEvent: PurchaseEvent) {
        val nfe = UUID.randomUUID().toString()
        val purchase = purchaseEvent.purchaseModel.copy(nfe = nfe)
        service.update(purchase)
    }
}