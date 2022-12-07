package com.github.stefankoppier.builder.dsl

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.assertThrows

class FakerTest {

    val faker = Faker()

    @Test
    fun int() {
        assertTrue { faker.int() in (Int.MIN_VALUE..Int.MAX_VALUE) }
    }

    @Test
    fun `int between equal numbers`() {
        assertEquals(1, faker.int(1, 1))
    }

    @Test
    fun long() {
        assertTrue { faker.long() in (Long.MIN_VALUE..Long.MAX_VALUE) }
    }

    @Test
    fun `long between equal numbers`() {
        assertEquals(1, faker.long(1, 1))
    }

    @Test
    fun boolean() {
        assertTrue { faker.boolean() in (false..true) }
    }

    @Test
    fun float() {
        assertTrue { faker.float() in (Float.MIN_VALUE..Float.MAX_VALUE) }
    }

    @Test
    fun `float between equal numbers`() {
        assertEquals(1.0f, faker.float(1.0f, 1.0f))
    }

    @Test
    fun double() {
        assertTrue { faker.double() in (Double.MIN_VALUE..Double.MAX_VALUE) }
    }

    @Test
    fun `double between equal numbers`() {
        assertEquals(1.0, faker.double(1.0, 1.0))
    }

    @Test
    fun char() {
        assertTrue { faker.char() in (('a'..'z') + ('A'..'Z') + ('0'..'9') + ' ') }
    }

    @Test
    fun `char in pool of only 'a'`() {
        assertEquals('a', faker.char(listOf('a')))
    }

    @Test
    fun string() {
        assertTrue { faker.string().matches(Regex("[a-zA-Z0-9 ]*")) }
    }

    @Test
    fun `string of negative minimum size`() {
        assertThrows<IllegalArgumentException> { faker.string(-1) }
    }

    @Test
    fun `string of minimum size greater than maximum size`() {
        assertThrows<IllegalArgumentException> { faker.string(2, 1) }
    }

    @Test
    fun enum() {
        assertTrue { faker.enum() in DayOfWeek.values() }
    }

    @Test
    fun `enum filter`() {
        assertEquals(DayOfWeek.FRIDAY, faker.enum { day -> day == DayOfWeek.FRIDAY })
    }

    @Test
    fun localDate() {
        assertTrue { faker.localDate().isBefore(LocalDate.of(2050, 12, 31)) }
    }

    @Test
    fun localTime() {
        faker.localTime()
    }

    @Test
    fun offsetDateTime() {
        val date = LocalDate.of(2050, 12, 31)
        val time = LocalTime.MAX
        assertTrue { faker.offsetDateTime().isBefore(OffsetDateTime.of(date, time, ZoneOffset.UTC)) }
    }
}
