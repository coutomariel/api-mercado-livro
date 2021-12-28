package com.mercadolivro.event

import buildPurchaseModel
import com.mercadolivro.service.PurchaseService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class GenerateNfeListenerTest {

    @MockK
    private lateinit var service: PurchaseService

    @InjectMockKs
    private lateinit var listener: GenerateNfeListener

    @Test
    fun `should generate nfe`() {
        val purchaseModel = buildPurchaseModel(nfe = null)

        val mockUUID = UUID.randomUUID()
        val expectedPurchaseModel = purchaseModel.copy(nfe = mockUUID.toString())
        mockkStatic(UUID::class)

        every { UUID.randomUUID() } returns mockUUID
        every { service.update(expectedPurchaseModel) } returns expectedPurchaseModel

        listener.generateNfe(PurchaseEvent(this, purchaseModel))

        verify(exactly = 1) { service.update(expectedPurchaseModel) }
    }

}