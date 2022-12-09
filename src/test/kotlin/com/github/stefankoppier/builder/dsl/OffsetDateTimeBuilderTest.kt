package com.github.stefankoppier.builder.dsl

import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class OffsetDateTimeBuilderTest {

    @Test
    fun random() {
        val faker = mock<Faker>()

        val expected = OffsetDateTime.of(LocalDate.EPOCH, LocalTime.NOON, ZoneOffset.UTC)
        whenever(faker.offsetDateTime()).thenReturn(expected)

        assertEquals(expected, OffsetDateTimeBuilder(faker)())
    }

    @Test
    fun constant() {
        val expected = OffsetDateTime.of(LocalDate.EPOCH, LocalTime.NOON, ZoneOffset.UTC)
        assertEquals(expected, OffsetDateTimeBuilder().constant(expected)())
    }
}
