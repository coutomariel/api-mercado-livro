package com.mercadolivro.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "customer")
data class CustomerModel(
    @Id
    val customerId: UUID = UUID.randomUUID(),
    var name: String,
    var email: String
)
