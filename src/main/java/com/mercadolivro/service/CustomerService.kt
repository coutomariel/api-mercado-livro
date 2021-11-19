package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.exception.CustomerNotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
    private val bookService: BookService,
    private val customersRepository: CustomerRepository
) {


    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customersRepository.findAll()
                .filter { customerModel ->
                    customerModel.name.contains(name, true)
                }
        }
        return customersRepository.findAll()
    }

    fun save(customer: CustomerModel): CustomerModel {
        return customersRepository.save(customer)
    }

    fun getById(id: String): CustomerModel {
        return customersRepository.findById(UUID.fromString(id))
            .orElseThrow { CustomerNotFoundException("Not found customer with ID: $id") }

    }

    fun remove(id: String) {
        getById(id).apply {
            this.customerStatus = CustomerStatus.INATIVO
        }.also {
            bookService.deleteByCustomer(it.customerId)
            customersRepository.save(it)
        }
    }
}