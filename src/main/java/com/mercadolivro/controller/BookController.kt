package com.mercadolivro.controller

import com.mercadolivro.controller.dto.*
import com.mercadolivro.model.BookModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomerService,
) {

    @PostMapping
    fun create(
        @RequestBody @Valid request: CreateBookRequest,
        URIbuilder: UriComponentsBuilder,
    ): ResponseEntity<BookResponse> {

        val bookCreated: BookModel = bookService.create(request.toModel())
        val uri = URIbuilder.path("/books/{id}").buildAndExpand(bookCreated.id).toUri()
        return ResponseEntity.created(uri).body(BookResponse.fromModel(bookCreated))

    }

    @GetMapping
    fun getAll(@PageableDefault(page = 0, size = 5) pageable: Pageable): PageResponse<BookResponse> {
        return bookService.getAll(pageable).map { book -> BookResponse.fromModel(book) }.toPageResponse()
    }

    @GetMapping("/actives")
    fun getAllActives(): List<BookResponse> {
        return bookService.getAllActives().map { book -> BookResponse.fromModel(book) }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): BookModel {
        return bookService.getById(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: Int) {
        bookService.deleteById(id)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody update: BookUpdate): ResponseEntity<BookResponse> {
        val updatedBook = bookService.update(id, update)
        return ResponseEntity.ok().body(BookResponse.fromModel(updatedBook))
    }
}
