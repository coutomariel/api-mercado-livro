package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
//    val id: UUID = UUID.randomUUID(),
    var name: String,
    var price: BigDecimal,
    @Enumerated(EnumType.STRING)
    var status: BookStatus = BookStatus.ATIVO,
)

