package com.mercadolivro.controller

import buildCustomerModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.config.security.UserCustomDetails
import com.mercadolivro.controller.dto.CreateCustomerRequest
import com.mercadolivro.controller.dto.CustomerUpdate
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.random.Random


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should return all customers`() {
        val customer1 = customerRepository.save(buildCustomerModel())
        val customer2 = customerRepository.save(buildCustomerModel())

        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))

            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].name").value(customer2.name))
            .andExpect(jsonPath("$[1].email").value(customer2.email))

    }

    @Test
    fun `should create customer`() {
        val request = CreateCustomerRequest("Baltazar Maria", "${Random.nextInt()}@email.com", "pass")

        mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated)

        val customers = customerRepository.findAll().toList()
        assertEquals(1, customers.size)
        assertEquals(request.name, customers[0].name)
        assertEquals(request.email, customers[0].email)
    }

    @Test
    fun `should return unprocessable entity when customer email is not available`() {
        val email = "${Random.nextInt()}@email.com"
        customerRepository.save(buildCustomerModel(email = email))
        val request = CreateCustomerRequest("Baltazar Maria", email, "pass")

        mockMvc.perform(post("/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.httpCode").value(422))
            .andExpect(jsonPath("$.message").value("Invalid request"))
    }


    @Test
    fun `should get user by id when user has same id`() {
        val customer = customerRepository.save(buildCustomerModel())

        mockMvc.perform(get("/customers/${customer.id}"))//.with(user(UserCustomDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))

    }

    @Test
    fun `should return not found when customer not exists`() {
        mockMvc.perform(get("/customers/21"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.httpCode").value(404))
            .andExpect(jsonPath("$.message").value("Not exists customer with ID: [21]"))
            .andExpect(jsonPath("$.internalCode").value("ML-101"))

    }

    @Test
    fun `should delete customer`() {
        val customer = customerRepository.save(buildCustomerModel(name = "Baltazar"))

        mockMvc.perform(delete("/customers/${customer.id}"))
            .andExpect(status().isNoContent)

        val customers = customerRepository.findAll().toList()
        assertEquals(customer.id, customers[0].id)
        assertEquals(CustomerStatus.INATIVO.name, customers[0].customerStatus.toString())
    }

    @Test
    fun `should update customer`() {
        val customer = customerRepository.save(buildCustomerModel(name = "Baltazar"))
        val request = CustomerUpdate("Jardel", "jardel@email.com")

        mockMvc.perform(put("/customers/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Jardel"))
            .andExpect(jsonPath("$.email").value("jardel@email.com"))

        val customers = customerRepository.findAll().toList()
        assertEquals(customer.id, customers[0].id)
        assertEquals(request.name, customers[0].name)
        assertEquals(request.email, customers[0].email)
    }

}