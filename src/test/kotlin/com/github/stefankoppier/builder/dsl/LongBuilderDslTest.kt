package com.github.stefankoppier.builder.dsl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

class LongBuilderDslTest {

    @Test
    fun `random`() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextLong()).thenReturn(1)
        assertEquals(1, LongBuilderDsl(faker)())
    }

    @Test
    fun `constant`() {
        assertEquals(2, LongBuilderDsl().constant(2)())
    }

    @Test
    fun `of`() {
        assertEquals(3, Long.of { constant(3) })
    }
}