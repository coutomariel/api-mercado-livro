package com.mercadolivro.controller.dto

import com.mercadolivro.model.CustomerModel
import com.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class CreateCustomerRequest(
    @field:NotEmpty val name: String,
    @field:Email @field:EmailAvailable val email: String,
    @field:NotEmpty val password: String
) {
    fun toModel(): CustomerModel {
        return CustomerModel(name = name, email = email, password = password)
    }
}
