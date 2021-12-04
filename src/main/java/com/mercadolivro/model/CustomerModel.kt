package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Profile
import java.util.*
import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity(name = "customer")
data class CustomerModel(
    @Id
    val customerId: UUID = UUID.randomUUID(),
    var name: String,
    var email: String,
    var password: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    var roles: Set<Profile> = setOf()
) {
    @Enumerated(EnumType.STRING)
    var customerStatus: CustomerStatus = CustomerStatus.ATIVO
        set(value) {
            if(customerStatus == CustomerStatus.INATIVO){
                throw Exception("Update customer status not possible, inative customer")
            }
            customerStatus = value
            field = value
        }

}
