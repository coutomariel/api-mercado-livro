package com.mercadolivro.config.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mercadolivro.exception.advice.ErrorDetails
import com.mercadolivro.exception.advice.ErrorType
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?,
    ) {

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorResponse = ErrorDetails(
            httpCode = HttpStatus.UNAUTHORIZED.value(),
            message = ErrorType.ML000.message,
            internalCode = ErrorType.ML000.code,
            errorFields = null
        )
        response.outputStream.print(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}