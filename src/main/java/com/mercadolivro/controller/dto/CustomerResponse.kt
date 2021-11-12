package com.mercadolivro.controller.dto

import com.mercadolivro.model.CustomerModel
import java.util.*

data class CustomerResponse(
    val id: UUID,
    val name: String,
    val email: String
) {
    companion object {
        fun fromModel(customerModel: CustomerModel): CustomerResponse {
            return CustomerResponse(
                id = customerModel.customerId,
                name = customerModel.name,
                email = customerModel.email
            )
        }
    }
}