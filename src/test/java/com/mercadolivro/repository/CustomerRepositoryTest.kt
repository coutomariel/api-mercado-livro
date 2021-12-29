package com.mercadolivro.repository

import buildCustomerModel
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should return customer containing`() {
        customerRepository.save(buildCustomerModel(name = "Patricia"))
        val julia = customerRepository.save(buildCustomerModel(name = "Julia"))
        val result = customerRepository.findByNameContaining("Jul")

        val expected = listOf(julia)

        assertEquals(expected, result)
        assertEquals(expected.size, result.size)
    }

    @Nested
    inner class `exists by email` {

        @Test
        fun `shoud return true when email exists`() {
            val email = "patricia@email.com"
            customerRepository.save(buildCustomerModel(name = "Patricia", email = email))
            val result = customerRepository.existsByEmail(email)
            assertTrue(result)
        }

        @Test
        fun `shoud return false when email not exists`() {
            val email = "julia@email.com"
            val result = customerRepository.existsByEmail(email)
            assertFalse(result)
        }
    }
}