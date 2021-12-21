package com.mercadolivro.config.security

import com.mercadolivro.config.security.filters.AuthenticationFilter
import com.mercadolivro.config.security.filters.AuthorizationFilter
import com.mercadolivro.config.security.jwt.JwtUtil
import com.mercadolivro.repository.CustomerRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailCustomService,
    private val jwtUtil: JwtUtil
) : WebSecurityConfigurerAdapter(){

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customers"
    )
    private val ADMIN_MATCHERS = arrayOf(
        "/customers"
    )

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
            .antMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description)
            .anyRequest().authenticated()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Add filter
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetails).passwordEncoder(bBcryptPasswordEncoder())
    }

    @Bean
    fun bBcryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}