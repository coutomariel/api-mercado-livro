package com.mercadolivro.repository

import com.mercadolivro.model.PurchaseModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PurchaseRepository : JpaRepository<PurchaseModel, UUID>