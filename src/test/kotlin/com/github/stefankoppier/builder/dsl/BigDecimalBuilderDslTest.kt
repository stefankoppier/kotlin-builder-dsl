package com.github.stefankoppier.builder.dsl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

class BigDecimalBuilderDslTest {

    @Test
    fun `random`() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextDouble()).thenReturn(1.0)
        assertEquals(BigDecimal.valueOf(1.0), BigDecimalBuilderDsl(faker)())
    }

    @Test
    fun `constant`() {
        assertEquals(BigDecimal.valueOf(2.0), BigDecimalBuilderDsl().constant(BigDecimal.valueOf(2.0))())
    }
}