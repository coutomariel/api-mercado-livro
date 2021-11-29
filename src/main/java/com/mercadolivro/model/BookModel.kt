package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity(name = "book")
data class BookModel(
    @Id
    val id: UUID = UUID.randomUUID(),
    var name: String,
    var price: BigDecimal,
    @Enumerated(EnumType.STRING)
    var status: BookStatus = BookStatus.ATIVO,
)

