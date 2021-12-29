package com.mercadolivro.controller

import buildBookModel
import buildCustomerModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.controller.dto.PostPurchaseRequest
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.repository.BookRepository
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.repository.PurchaseRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockkClass
import io.mockk.runs
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
class PurchaseControllerTest {

    @Autowired
    private lateinit var purchaseRepository: PurchaseRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        purchaseRepository.deleteAll()
        bookRepository.deleteAll()
        purchaseRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        purchaseRepository.deleteAll()
        bookRepository.deleteAll()
        purchaseRepository.deleteAll()
    }

    @Test
    fun `should create purchase`() {
        val customer = customerRepository.save(buildCustomerModel(name = "Baltazar"))
        val bdd = bookRepository.save(buildBookModel(name = "BDD"))
        val request = PostPurchaseRequest(customer.id!!, setOf(bdd.id!!))

        val publisher = mockkClass(ApplicationEventPublisher::class)
        every { publisher.publishEvent(any()) } just runs

        mockMvc.perform(post("/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

    }

    @Test
    fun `should return not found when customer not exists`() {
        val bdd = bookRepository.save(buildBookModel(name = "BDD"))
        val request = PostPurchaseRequest(1, setOf(bdd.id!!))

        val publisher = mockkClass(ApplicationEventPublisher::class)
        every { publisher.publishEvent(any()) } just runs
        mockMvc.perform(post("/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.httpCode").value("404"))
            .andExpect(jsonPath("$.internalCode").value("ML-101"))
            .andExpect(jsonPath("$.message").value("Not exists customer with ID: [1]"))

    }

    @Test
    fun `should return unproccesable entity  when book was sold`() {
        val customer = customerRepository.save(buildCustomerModel(name = "Baltazar"))
        val bdd = bookRepository.save(buildBookModel(name = "BDD").apply { status = BookStatus.VENDIDO })
        val request = PostPurchaseRequest(customer.id!!, setOf(bdd.id!!))

        val publisher = mockkClass(ApplicationEventPublisher::class)

        every { publisher.publishEvent(any()) } just runs
        mockMvc.perform(post("/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.httpCode").value(422))
            .andExpect(jsonPath("$.message").value("Was sold book with ID: [${bdd.id!!}]"))
            .andExpect(jsonPath("$.internalCode").value("ML-202"))
    }
}


