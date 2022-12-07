package com.github.stefankoppier.builder.dsl

import java.time.DayOfWeek
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class EnumBuilderDslTest {

    private class TestEnumBuilderDsl(faker: Faker) : EnumBuilderDsl<DayOfWeek>(faker) {
        override fun allValues(): Array<DayOfWeek> {
            return DayOfWeek.values()
        }
    }

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.int(0, DayOfWeek.values().size)).thenReturn(0)
        assertEquals(DayOfWeek.MONDAY, TestEnumBuilderDsl(faker)())
    }

    @Test
    fun filter() {
        val faker = mock<Faker>()
        whenever(faker.int()).thenReturn(0)
        assertEquals(DayOfWeek.FRIDAY, TestEnumBuilderDsl(faker).filter { it == DayOfWeek.FRIDAY }())
    }
}
