package com.mercadolivro.service

import buildBookModel
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.event.PurchaseEvent
import com.mercadolivro.exception.BookSoldException
import com.mercadolivro.model.BookModel
import com.mercadolivro.repository.BookRepository
import io.mockk.*
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class BookServiceTest {

    @MockK
    private lateinit var bookRepository: BookRepository

    @InjectMockKs
    private lateinit var bookService: BookService

    val slotBookRepository = slot<List<BookModel>>()

    @Test
    fun `should update book status to sold`() {
        val tdd = buildBookModel(id = 1, name = "TDD").apply { status = BookStatus.ATIVO }
        val cleanCode = buildBookModel(id = 2, name = "CleanCode").apply { status = BookStatus.ATIVO }
        val booksToSale = listOf(tdd, cleanCode)
        println(booksToSale)

        val soldBooks = listOf(tdd.copy(status = BookStatus.VENDIDO), cleanCode.copy(status = BookStatus.VENDIDO))
        every { bookRepository.saveAll(soldBooks) } returns soldBooks

        bookService.soldBook(booksToSale)

        verify { bookRepository.saveAll(capture(slotBookRepository)) }
        assertEquals(2, slotBookRepository.captured.size)
        assertEquals(BookStatus.VENDIDO.name, slotBookRepository.captured[0].status.name)
        assertEquals(BookStatus.VENDIDO.name, slotBookRepository.captured[1].status.name)

    }

    @Test
    fun `should retunr unprocessable entity when book was sold`() {
        val tdd = buildBookModel(id = 1, name = "TDD").apply { status = BookStatus.VENDIDO }

        val error = assertThrows<BookSoldException> {
            bookService.soldBook(listOf(tdd))
        }
        assertEquals("ML-202", error.internalCode)
        assertEquals("Was sold book with ID: [${tdd.id}]", error.message)

    }


}