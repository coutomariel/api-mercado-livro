package com.mercadolivro.repository

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<BookModel, Int> {
    fun findByStatus(status: BookStatus): List<BookModel>
//    fun findByCustomerCustomerId(customerId: UUID): List<BookModel>
}
