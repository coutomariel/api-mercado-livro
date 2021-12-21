package com.mercadolivro.config.security

import com.mercadolivro.repository.CustomerRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailCustomService(
    private val customerRepository: CustomerRepository,
) : UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toInt())
            .orElseThrow { AuthenticationException("Usuário não encontrado", "999") }

        return UserCustomDetails(customer)
    }

}