package com.mercadolivro.service

import com.mercadolivro.exception.CustomerNotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomerService(
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

    fun create(customer: CustomerModel): CustomerModel {
        return customersRepository.save(customer)
    }

    fun getCustomer(id: String): CustomerModel {
        if (!customersRepository.existsById(UUID.fromString(id))) {
            throw CustomerNotFoundException("Not found customer with ID: $id")
        }
        return customersRepository.getById(UUID.fromString(id))

    }

    fun remove(id: String) {
        val uuid = UUID.fromString(id)
        if(!customersRepository.existsById(uuid)){
            throw CustomerNotFoundException("Not found customer with ID: $id")
        }
        customersRepository.deleteById(uuid)
    }

    fun update(customer: CustomerModel): CustomerModel {
        if(!customersRepository.existsById(customer.customerId)){
            throw CustomerNotFoundException("Not found customer with ID: ${customer.customerId}")
        }
        return customersRepository.save(customer)
    }

}