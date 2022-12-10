package com.github.stefankoppier.builder.dsl.containers

import com.github.stefankoppier.builder.dsl.Faker
import com.github.stefankoppier.builder.dsl.primitives.IntBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ListBuilderTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.int(0, 10)).thenReturn(2)
        val factory = IntBuilder(faker).constant(1)
        val result = ListBuilder(factory, faker)()
        assertEquals(listOf(1, 1), result)
    }

    @Test
    fun between() {
        val result = ListBuilder(IntBuilder()).between(3, 3)()
        assertEquals(3, result.size)
    }
}
