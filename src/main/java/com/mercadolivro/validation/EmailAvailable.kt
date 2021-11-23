package com.mercadolivro.validation

import com.mercadolivro.service.CustomerService
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailAvailableValidator::class])
@MustBeDocumented
annotation class EmailAvailable(
    val message: String = "Please, this email does not available",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

class EmailAvailableValidator(val customerService: CustomerService) : ConstraintValidator<EmailAvailable, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        return !customerService.existsByEmail(value)
    }
}
