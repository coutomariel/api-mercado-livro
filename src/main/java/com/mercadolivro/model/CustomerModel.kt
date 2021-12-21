package com.mercadolivro.model

import com.mercadolivro.config.security.Role
import com.mercadolivro.enums.CustomerStatus
import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
//    val customerId: UUID = UUID.randomUUID(),
    var name: String,
    var email: String,
    var password: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    var roles: Set<Role> = setOf()
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
