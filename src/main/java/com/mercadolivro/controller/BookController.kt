package com.mercadolivro.controller

import com.mercadolivro.controller.dto.BookResponse
import com.mercadolivro.controller.dto.BookUpdate
import com.mercadolivro.controller.dto.CreateBookRequest
import com.mercadolivro.model.BookModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/books")
class BookController(
    val bookService: BookService,
    val customerService: CustomerService
) {

    @PostMapping
    fun create(
        @RequestBody request: CreateBookRequest,
        URIbuilder: UriComponentsBuilder
    ): ResponseEntity<BookResponse> {
        val customer = customerService.getById(request.customerId)

        val bookCreated: BookModel = bookService.create(request.toModel(customer))
        val uri = URIbuilder.path("/books/{id}").buildAndExpand(bookCreated.id).toUri()
        return ResponseEntity.created(uri).body(BookResponse.fromModel(bookCreated))

    }

    @GetMapping
    fun getAll(): List<BookResponse> {
        return bookService.getAll().map { book -> BookResponse.fromModel(book) }
    }

    @GetMapping("/actives")
    fun getAllActives(): List<BookResponse> {
        return bookService.getAllActives().map { book -> BookResponse.fromModel(book) }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): BookModel {
        return bookService.getById(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: UUID){
        bookService.deleteById(id)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody update: BookUpdate): ResponseEntity<BookResponse> {
        val updatedBook = bookService.update(id, update)
        return ResponseEntity.ok().body(BookResponse.fromModel(updatedBook))
    }
}
