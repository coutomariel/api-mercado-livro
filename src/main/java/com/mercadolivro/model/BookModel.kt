package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity(name = "book")
data class BookModel(
    @Id
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var price: BigDecimal,
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null,
    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
)
