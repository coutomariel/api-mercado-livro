package com.mercadolivro.controller.dto

import com.mercadolivro.model.CustomerModel
import java.util.*

data class CustomerUpdate (val name: String, val email:String) {
    fun toModel(id: String, request: CustomerUpdate): CustomerModel {
        return CustomerModel(
            customerId = UUID.fromString(id),
            name = request.name,
            email = request.email
        )
    }
}
