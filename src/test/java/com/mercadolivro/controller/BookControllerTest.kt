package com.mercadolivro.controller

import buildBookModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.controller.dto.BookUpdate
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.repository.BookRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class BookControllerTest {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() = bookRepository.deleteAll()

    @AfterEach
    fun tearDown() = bookRepository.deleteAll()

    @Test
    fun `should create book`() {
        val request = buildBookModel(name = "BDD", price = BigDecimal(89.9))

        mockMvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

    }

    @Test
    fun `should return unprocessable entity when name is empty`() {
        val request = buildBookModel(name = "")

        mockMvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.message").value("Invalid request"))
            .andExpect(jsonPath("$.errorFields.size()").value(1))
    }

    @Test
    fun `should return unprocessable entity when email is empty`() {
        val request = buildBookModel(name = "")

        mockMvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.message").value("Invalid request"))
    }

    @Test
    fun `should return all books`() {
        bookRepository.save(buildBookModel(name = "TDD"))
        bookRepository.save(buildBookModel(name = "Clean Code"))
        val inactive = buildBookModel(name = "Design Patterns").apply {
            this.status = BookStatus.DELETADO
        }
        bookRepository.save(inactive)


        mockMvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.items.size()").value(3))

        val books = bookRepository.findAll().toList()
        assertEquals(3, books.size)

    }

    @Test
    fun `should return active books`() {
        bookRepository.save(buildBookModel(name = "TDD"))
        bookRepository.save(buildBookModel(name = "Clean Code"))
        val inactive = buildBookModel(name = "Design Patterns").apply {
            this.status = BookStatus.DELETADO
        }
        bookRepository.save(inactive)


        mockMvc.perform(get("/books/actives"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(2))

        val books = bookRepository.findAll().toList()
        assertEquals(3, books.size)

    }

    @Test
    fun `should return not found when book not exists by id`() {

        mockMvc.perform(get("/books/35"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.httpCode").value(404))
            .andExpect(jsonPath("$.message").value("Not exists book with ID: [35]"))
            .andExpect(jsonPath("$.internalCode").value("ML-201"))
    }

    @Test
    fun `should return bad request when informed caractere id`() {

        mockMvc.perform(get("/books/${UUID.randomUUID()}"))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.httpCode").value(400))
            .andExpect(jsonPath("$.message").value("Invalid parameter argument"))
            .andExpect(jsonPath("$.internalCode").value("ML-002"))
    }

    @Test
    fun `should return a book by exists id`() {
        val book = bookRepository.save(buildBookModel(name = "TDD", price = BigDecimal(89.9)))

        mockMvc.perform(get("/books/${book.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("TDD"))
            .andExpect(jsonPath("$.price").value(BigDecimal(89.9).setScale(1, RoundingMode.HALF_DOWN)))
            .andExpect(jsonPath("$.status").value("ATIVO"))
    }

    @Test
    fun `should delete a book`() {
        val book = bookRepository.save(buildBookModel(name = "TDD", price = BigDecimal(89.9)))

        mockMvc.perform(delete("/books/${book.id}"))
            .andExpect(status().isNoContent)

        val deletedBooks = bookRepository.findById(book.id!!)
        assertEquals("DELETADO", deletedBooks.get().status.name)
    }

    @Test
    fun `should update a book`() {
        val book = bookRepository.save(buildBookModel(name = "TDD", price = BigDecimal(89.9)))

        val request = BookUpdate("TDD Update", null)

        mockMvc.perform(put("/books/${book.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("TDD Update"))

        val updatedBook = bookRepository.findById(book.id!!)
        assertEquals("TDD Update", updatedBook.get().name)
    }


}