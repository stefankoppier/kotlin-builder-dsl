package com.github.stefankoppier.builder.dsl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import kotlin.test.assertEquals

class IntBuilderDslTest {

    @Test
    fun `random`() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextInt()).thenReturn(1)
        assertEquals(1, IntBuilderDsl(faker)())
    }

    @Test
    fun `constant`() {
        assertEquals(2, IntBuilderDsl().constant(2)())
    }

    @Test
    fun `of`() {
        assertEquals(3, Int.of { constant(3) })
    }
}