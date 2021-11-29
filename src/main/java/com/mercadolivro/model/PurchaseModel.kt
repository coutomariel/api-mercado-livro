package com.mercadolivro.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "purchase")
data class PurchaseModel(
    @Id
    var id: UUID? = UUID.randomUUID(),
    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customerModel: CustomerModel,

    @ManyToMany
    @JoinTable(
        name = "purchase_book",
        joinColumns = [JoinColumn(name = "purchase_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    val books: List<BookModel>,
    val nfe: String?,
    val price: BigDecimal,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
)