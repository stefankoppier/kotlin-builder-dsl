package com.github.stefankoppier.builder.dsl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

class StringBuilderDslTest {

    @Test
    fun `random`() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextString()).thenReturn("1")
        assertEquals("1", StringBuilderDsl(faker)())
    }

    @Test
    fun `constant`() {
        assertEquals("2", StringBuilderDsl().constant("2")())
    }

    @Test
    fun `of`() {
        assertEquals("3", String.of { constant("3") })
    }
}