package com.mercadolivro.service

import buildCustomerModel
import com.mercadolivro.enums.CustomerStatus.INATIVO
import com.mercadolivro.exception.CustomerNotFoundException
import com.mercadolivro.exception.advice.ErrorType
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*


@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    private lateinit var bookService: BookService;

    @MockK
    private lateinit var customersRepository: CustomerRepository;

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    @SpyK
    private lateinit var customerService: CustomerService

    @Test
    fun `should return all customers`() {

        val fakeCustomers = arrayListOf(buildCustomerModel(id = 1), buildCustomerModel(id = 2))
        every { customersRepository.findAll() } returns fakeCustomers

        val customers = customerService.getAll(null)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { customersRepository.findAll() }
        verify(exactly = 0) { customersRepository.findByNameContaining(any()) }
    }

    @Test
    fun `when inform email, should return all customers`() {

        val name = UUID.randomUUID().toString()
        val fakeCustomers = arrayListOf(buildCustomerModel(id = 1), buildCustomerModel(id = 2))
        every { customersRepository.findByNameContaining(name) } returns fakeCustomers

        val customers = customerService.getAll(name)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 0) { customersRepository.findAll() }
        verify(exactly = 1) { customersRepository.findByNameContaining(name) }
    }

    @Test
    fun `should save customer`() {
        val initialPassword = Math.random().toString()
        val customerModel = buildCustomerModel(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()

        every { bCrypt.encode(initialPassword) } returns fakePassword
        every { customersRepository.save(any()) } returns customerModel

        customerService.save(customerModel)

        verify(exactly = 1) { bCrypt.encode(any()) }
        verify(exactly = 1) { customersRepository.save(any()) }
    }

    @Test
    fun `should return customer by id`() {
        val fakeId = Math.random().toInt()
        val fakeCustomer = buildCustomerModel(id = fakeId)
        every { customersRepository.findById(fakeId) } returns Optional.of(fakeCustomer)

        val customer = customerService.getById(fakeId)

        assertEquals(fakeCustomer, customer)
        verify(exactly = 1) { customersRepository.findById(fakeId) }
    }

    @Test
    fun `should throw customer not found exception when customer not exists by id`() {
        val id = Math.random().toInt()
        every { customersRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<CustomerNotFoundException> {
            customerService.getById(id)
        }

        assertEquals(ErrorType.ML101.message.format(id), error.message)

    }

    @Test
    fun `should delete customer by id`() {
        val ramdomId = Math.random().toInt()
        val fakeCustomer = buildCustomerModel(id = ramdomId)
        every { customerService.getById(ramdomId) } returns fakeCustomer

        val expectedCustomer = fakeCustomer.copy(
            customerStatus = INATIVO
        )
        every { customersRepository.save(expectedCustomer) } returns expectedCustomer

        customerService.remove(ramdomId)
        verify(exactly = 1) { customersRepository.save(expectedCustomer) }

    }

    @Test
    fun `should throw customer not found exception when customer not exists`() {
        val ramdomId = Math.random().toInt()
        val expectedException = CustomerNotFoundException(ErrorType.ML101.message.format(ramdomId), ErrorType.ML101.code)
        every { customerService.getById(ramdomId) } throws expectedException

        val error = assertThrows<CustomerNotFoundException> {
            customerService.remove(ramdomId)
        }

        assertEquals(expectedException.message, error.message)
        assertEquals(expectedException.internalCode, error.internalCode)


    }

    @Test
    fun `should verify if email is available`() {
        val email = "${UUID.randomUUID()}@email.com"
        every { customersRepository.existsByEmail(email) } returns true

        val emailAvailable = customerService.existsByEmail(email)

        assertTrue(emailAvailable)
    }

}