package com.mercadolivro.controller

import com.mercadolivro.controller.dto.PostPurchaseRequest
import com.mercadolivro.controller.mapper.PurchaseMapper
import com.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/purchase")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: PostPurchaseRequest) {
        purchaseService.create(purchaseMapper.toModel(request))
    }
}