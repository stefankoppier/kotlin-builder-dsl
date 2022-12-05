package com.github.stefankoppier.builder.dsl

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.assertEquals

class OffsetDateTimeBuilderDslTest {

    @Test
    fun `random`() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextLocalDate()).thenReturn(LocalDate.EPOCH)
        whenever(faker.nextLocalTime()).thenReturn(LocalTime.NOON)

        val expected = OffsetDateTime.of(LocalDate.EPOCH, LocalTime.NOON, ZoneOffset.UTC)
        assertEquals(expected, OffsetDateTimeBuilderDsl(faker)())
    }

    @Test
    fun `constant`() {
        val expected = OffsetDateTime.of(LocalDate.EPOCH, LocalTime.NOON, ZoneOffset.UTC)
        assertEquals(expected, OffsetDateTimeBuilderDsl().constant(expected)())
    }
}