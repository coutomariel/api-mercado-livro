package com.mercadolivro.controller.dto

import com.mercadolivro.model.CustomerModel

data class CreateCustomerRequest(val name: String, val email: String) {
    fun toModel(): CustomerModel {
        return CustomerModel(name = name, email = email)
    }
}
