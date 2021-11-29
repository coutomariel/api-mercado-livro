package com.mercadolivro.validation

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UUIDValidator::class])
@MustBeDocumented
annotation class ValidUUID(
    val message: String = "ID does not UUID valid",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

class UUIDValidator : ConstraintValidator<ValidUUID, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        return try {
            java.util.UUID.fromString(value)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
    }
}
