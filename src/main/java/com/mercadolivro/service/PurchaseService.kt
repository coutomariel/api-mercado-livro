package com.mercadolivro.service

import com.mercadolivro.event.PurchaseEvent
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val publisher: ApplicationEventPublisher,
) {

    fun create(purchase: PurchaseModel): PurchaseModel {
        val purchase = purchaseRepository.save(purchase)
        publisher.publishEvent(PurchaseEvent(this, purchase))
        return purchase
    }

    fun update(purchase: PurchaseModel): PurchaseModel {
        return purchaseRepository.save(purchase)
    }

}
