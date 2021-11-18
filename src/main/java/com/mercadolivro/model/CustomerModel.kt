package com.mercadolivro.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "customer")
data class CustomerModel(
    @Id
    val customerId: UUID = UUID.randomUUID(),
    val name: String,
    val email: String
)
