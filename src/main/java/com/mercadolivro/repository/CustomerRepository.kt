package com.mercadolivro.repository

import com.mercadolivro.model.CustomerModel
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<CustomerModel, Int> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String) : CustomerModel?
    fun findByNameContaining(name: String) : List<CustomerModel>
}