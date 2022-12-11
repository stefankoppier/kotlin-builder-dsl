package io.github.stefankoppier.builder.dsl

import java.time.DayOfWeek
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class EnumBuilderTest {

    private class TestEnumBuilder(faker: Faker) : EnumBuilder<DayOfWeek>(faker) {
        override fun allValues(): Array<DayOfWeek> {
            return DayOfWeek.values()
        }
    }

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.int(0, DayOfWeek.values().size)).thenReturn(0)
        assertEquals(DayOfWeek.MONDAY, TestEnumBuilder(faker)())
    }

    @Test
    fun filter() {
        val faker = mock<Faker>()
        whenever(faker.int()).thenReturn(0)
        assertEquals(DayOfWeek.FRIDAY, TestEnumBuilder(faker).filter { it == DayOfWeek.FRIDAY }())
    }
}
