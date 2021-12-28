package com.mercadolivro.service

import buildPurchaseModel
import com.mercadolivro.event.PurchaseEvent
import com.mercadolivro.repository.PurchaseRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher

@ExtendWith(MockKExtension::class)
class PurchaseServiceTest {

    @MockK
    private lateinit var purchaseRepository: PurchaseRepository
    @MockK
    private lateinit var publisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var purchaseService: PurchaseService

    val purchaseEventSlot = slot<PurchaseEvent>()

    @Test
    fun `should create purchase and publish event`(){
        val purchaseModel = buildPurchaseModel(nfe = null)
        every { purchaseRepository.save(purchaseModel) } returns purchaseModel
        every { publisher.publishEvent(any()) } just runs

        purchaseService.create(purchaseModel)

        verify(exactly = 1) { purchaseRepository.save(purchaseModel) }
        verify(exactly = 1) { publisher.publishEvent(capture(purchaseEventSlot)) }

        Assertions.assertEquals(purchaseModel, purchaseEventSlot.captured.purchaseModel)
    }

    @Test
    fun `should update purchase`() {
        val purchaseModel = buildPurchaseModel(nfe = null)
        every { purchaseRepository.save(purchaseModel) } returns purchaseModel

        purchaseService.update(purchaseModel)

        verify(exactly = 1) { purchaseRepository.save(purchaseModel) }
    }
}