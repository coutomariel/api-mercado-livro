package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity(name = "customer")
data class CustomerModel(
    @Id
    val customerId: UUID = UUID.randomUUID(),
    @Enumerated(EnumType.STRING)
    var customerStatus: CustomerStatus = CustomerStatus.ATIVO,
    var name: String,
    var email: String
)
