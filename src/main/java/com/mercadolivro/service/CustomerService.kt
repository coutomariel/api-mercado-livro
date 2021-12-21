package com.mercadolivro.service

import com.mercadolivro.config.security.Role
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.exception.CustomerNotFoundException
import com.mercadolivro.exception.advice.ErrorType
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val bookService: BookService,
    private val customersRepository: CustomerRepository,
    private val bcryp: BCryptPasswordEncoder
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
        val customerToSave = customer.copy(
            roles = setOf(Role.CUSTOMER),
            password = bcryp.encode(customer.password)
        )
        return customersRepository.save(customerToSave)
    }

    fun getById(id: Int): CustomerModel {
        return customersRepository.findById(id)
            .orElseThrow { CustomerNotFoundException(ErrorType.ML101.message.format(id), ErrorType.ML101.code) }

    }

    fun remove(id: Int) {
        getById(id).apply {
            this.customerStatus = CustomerStatus.INATIVO
        }
//            .also {
//            bookService.deleteByCustomer(it.customerId)
//            customersRepository.save(it)
//        }
    }

    fun existsByEmail(email: String): Boolean {
        return customersRepository.existsByEmail(email)
    }
}