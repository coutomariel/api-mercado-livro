package com.mercadolivro.repository

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository: JpaRepository<BookModel, UUID> {
    fun findByStatus(status: BookStatus): List<BookModel>
//    fun findByCustomerCustomerId(customerId: UUID): List<BookModel>
}
