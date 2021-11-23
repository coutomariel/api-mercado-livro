package com.mercadolivro.validation

import com.mercadolivro.exception.UUIDNotValidException
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UUIDValidator::class])
@MustBeDocumented
annotation class UUID(
    val message: String = "ID does not UUID valid",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

class UUIDValidator : ConstraintValidator<UUID, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        return try {
            java.util.UUID.fromString(value)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
    }
}
