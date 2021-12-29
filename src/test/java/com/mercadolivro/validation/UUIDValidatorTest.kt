package com.mercadolivro.validation

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UUIDValidatorTest {

    @InjectMockKs
    private lateinit var uuidValidator: UUIDValidator

    @Test
    fun `shold return true when parameter is valid UUID`() {
        val valid = uuidValidator.isValid("b8c9300c-68e5-11ec-90d6-0242ac120003", null)
        assertTrue(valid)
    }

    @Test
    fun `shold thow illegal argument exception when not possible convert`() {
        val valid = uuidValidator.isValid("1111222233334444-1111222233334444-1111222233334444-1111222233334444-1111222233334444", null)
        assertFalse(valid)
    }

    @Test
    fun `shold thow illegala argument exception when not possible convert`() {
        val valid = uuidValidator.isValid("This-is-not-a-UUID", null)
        assertFalse(valid)
    }


}