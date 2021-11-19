package com.mercadolivro.controller.dto

import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.CustomerService

data class CustomerUpdate (val name: String, val email:String) {
    fun toModel(id: String, request: CustomerUpdate, customerService: CustomerService): CustomerModel {
        val customerModel: CustomerModel = customerService.getById(id)
        customerModel.apply {
            this.name = request.name
            this.email = request.email
        }
        return customerModel
    }
}
