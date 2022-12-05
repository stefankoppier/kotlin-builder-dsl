package com.github.stefankoppier.builder.dsl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

class FloatBuilderDslTest {

    @Test
    fun `random`() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextFloat()).thenReturn(1.0f)
        assertEquals(1.0f, FloatBuilderDsl(faker)())
    }

    @Test
    fun `constant`() {
        assertEquals(2.0f, FloatBuilderDsl().constant(2.0f)())
    }

    @Test
    fun `of`() {
        assertEquals(3.0f, Float.of { constant(3.0f) })
    }
}