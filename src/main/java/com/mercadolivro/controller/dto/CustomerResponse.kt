package com.mercadolivro.controller.dto

import com.mercadolivro.model.CustomerModel

data class CustomerResponse(
    val id: Int,
    val name: String,
    val email: String
) {
    companion object {
        fun fromModel(customerModel: CustomerModel): CustomerResponse {
            return CustomerResponse(
                id = customerModel.id!!,
                name = customerModel.name,
                email = customerModel.email
            )
        }
    }
}