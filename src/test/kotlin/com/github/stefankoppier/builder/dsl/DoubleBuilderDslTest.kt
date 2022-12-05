package com.github.stefankoppier.builder.dsl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

class DoubleBuilderDslTest {

    @Test
    fun `random`() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextDouble()).thenReturn(1.0)
        assertEquals(1.0, DoubleBuilderDsl(faker)())
    }

    @Test
    fun `constant`() {
        assertEquals(2.0, DoubleBuilderDsl().constant(2.0)())
    }

    @Test
    fun `of`() {
        assertEquals(3.0, Double.of { constant(3.0) })
    }
}