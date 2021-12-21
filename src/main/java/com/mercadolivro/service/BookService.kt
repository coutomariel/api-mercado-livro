package com.mercadolivro.service

import com.mercadolivro.controller.dto.BookUpdate
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.exception.BookNotFoundException
import com.mercadolivro.exception.BookSoldException
import com.mercadolivro.exception.advice.ErrorType
import com.mercadolivro.model.BookModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

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

    fun getById(id: Int): BookModel {
        return bookRepository.findById(id)
            .orElseThrow { BookNotFoundException(ErrorType.ML201.message.format(id), ErrorType.ML201.code) }
    }

    fun getAllActives(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun deleteById(id: Int) {
        getById(id)
            .apply {
                status = BookStatus.DELETADO
            }.also {
                bookRepository.save(it)
            }
    }

    fun update(id: Int, update: BookUpdate): BookModel {
        getById(id)
            .apply {
                this.name = update.name ?: this.name
                this.price = update.price ?: this.price
            }.also {
                return bookRepository.save(it)
            }
    }

    fun getAllIds(books: Set<Int>): List<BookModel> {
        books.map { it }.let {
            return bookRepository.findAllById(it)
        }
    }

    fun soldBook(books: List<BookModel>) {
        books.forEach { book ->
            if (book.status == BookStatus.VENDIDO) {
                throw BookSoldException(ErrorType.ML202.message.format(book.id), ErrorType.ML202.code)
            }
            book.status = BookStatus.VENDIDO
        }
        bookRepository.saveAll(books)
    }

}
