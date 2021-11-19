package com.mercadolivro.service

import com.mercadolivro.controller.dto.BookUpdate
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.exception.BookNotFoundException
import com.mercadolivro.model.BookModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun create(bookModel: BookModel): BookModel {
        return bookRepository.save(bookModel)
    }

    fun getAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun getById(id: UUID): BookModel {
        return bookRepository.findById(id).orElseThrow { BookNotFoundException("Not found a book with ID:$id") }
    }

    fun getAllActives(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun deleteById(id: UUID) {
        getById(id)
            .apply {
                status = BookStatus.DELETADO
            }.also {
                bookRepository.save(it)
            }
    }

    fun update(id: UUID, update: BookUpdate): BookModel {
        getById(id)
            .apply {
                this.name = update.name ?: this.name
                this.price = update.price ?: this.price
            }.also {
                return bookRepository.save(it)
            }
    }

    fun deleteByCustomer(customerId: UUID) {
        val books: List<BookModel> = bookRepository.findByCustomerCustomerId(customerId)
        for (book in books) {
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }

}
