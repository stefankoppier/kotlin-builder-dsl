package io.github.stefankoppier.builder.dsl.generators

import io.github.stefankoppier.builder.dsl.Faker
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class OffsetDateTimeGeneratorTest {

    @Test
    fun random() {
        val faker = mock<Faker>()

        val expected = OffsetDateTime.of(LocalDate.EPOCH, LocalTime.NOON, ZoneOffset.UTC)
        whenever(faker.offsetDateTime()).thenReturn(expected)

        assertEquals(expected, OffsetDateTimeGenerator(faker)())
    }

    @Test
    fun constant() {
        val expected = OffsetDateTime.of(LocalDate.EPOCH, LocalTime.NOON, ZoneOffset.UTC)
        assertEquals(expected, OffsetDateTimeGenerator().constant(expected)())
    }
}
