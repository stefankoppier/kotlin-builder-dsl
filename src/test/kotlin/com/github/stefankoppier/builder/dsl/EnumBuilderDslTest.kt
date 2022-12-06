package com.github.stefankoppier.builder.dsl

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class EnumBuilderDslTest {

    private enum class TestEnum {
        FIRST,
        SECOND
    }

    private class TestEnumBuilderDsl(faker: Faker) : EnumBuilderDsl<TestEnum>(faker) {
        override fun allValues(): Array<TestEnum> {
            return TestEnum.values()
        }
    }

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.nextInt(0, 2)).thenReturn(0)
        assertEquals(TestEnum.FIRST, TestEnumBuilderDsl(faker)())
    }

    @Test
    fun filter() {
        val faker = mock<Faker>()
        whenever(faker.nextInt()).thenReturn(0)
        assertEquals(TestEnum.SECOND, TestEnumBuilderDsl(faker).filter { it != TestEnum.FIRST }())
    }
}
