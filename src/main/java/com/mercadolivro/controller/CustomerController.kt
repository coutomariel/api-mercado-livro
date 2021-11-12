package com.mercadolivro.controller

import com.mercadolivro.controller.dto.CreateCustomerRequest
import com.mercadolivro.controller.dto.CustomerResponse
import com.mercadolivro.controller.dto.CustomerUpdate
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/customers")
class CustomerController(
    val customerService: CustomerService
) {

    @PostMapping
    fun createCustomer(
        @RequestBody createCustomerRequest: CreateCustomerRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<CustomerResponse> {

        val customer: CustomerModel = customerService.create(createCustomerRequest.toModel())

        val response = CustomerResponse.fromModel(customer)
        val uri: URI = uriBuilder.path("/customers/{id}").buildAndExpand(response.id).toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerResponse> {
        return customerService.getAll(name).map { customer -> CustomerResponse.fromModel(customer) }
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: String): CustomerResponse {
        val customer: CustomerModel = customerService.getCustomer(id)
        return CustomerResponse.fromModel(customer)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: String) {
        customerService.remove(id)
    }

    @PutMapping("/{id}")
    fun updateCustomer(@PathVariable id: String, @RequestBody request: CustomerUpdate): ResponseEntity<Any> {
        val customer = customerService.update(request.toModel(id, request))
        return ResponseEntity.ok().body(CustomerResponse.fromModel(customer))
    }
}