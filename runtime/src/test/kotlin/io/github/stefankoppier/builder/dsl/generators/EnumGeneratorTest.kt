package io.github.stefankoppier.builder.dsl.generators

import io.github.stefankoppier.builder.dsl.Faker
import java.time.DayOfWeek
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class EnumGeneratorTest {

    private class TestEnumGenerator(faker: Faker) : EnumGenerator<DayOfWeek, TestEnumGenerator>(faker) {
        override fun allValues(): Array<DayOfWeek> {
            return DayOfWeek.values()
        }
    }

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.int(0, DayOfWeek.values().size)).thenReturn(0)
        assertEquals(DayOfWeek.MONDAY, TestEnumGenerator(faker)())
    }

    @Test
    fun filter() {
        val faker = mock<Faker>()
        whenever(faker.int()).thenReturn(0)
        assertEquals(DayOfWeek.FRIDAY, TestEnumGenerator(faker).filter { it == DayOfWeek.FRIDAY }())
    }
}