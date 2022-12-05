package com.github.stefankoppier.builder.dsl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.test.assertEquals

class LocalDateBuilderDslTest {

    @Test
    fun `random`() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextLocalDate()).thenReturn(LocalDate.EPOCH)
        assertEquals(LocalDate.EPOCH, LocalDateBuilderDsl(faker)())
    }

    @Test
    fun `constant`() {
        assertEquals(LocalDate.EPOCH, LocalDateBuilderDsl().constant(LocalDate.EPOCH)())
    }
}