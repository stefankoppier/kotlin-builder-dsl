package com.github.stefankoppier.builder.dsl

import com.github.stefankoppier.builder.dsl.primitives.IntBuilderDsl
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ListBuilderDslTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.int(0, 10)).thenReturn(2)
        val factory = IntBuilderDsl(faker).constant(1)
        val result = ListBuilderDsl(factory, faker)()
        assertEquals(listOf(1, 1), result)
    }

    @Test
    fun between() {
        val result = ListBuilderDsl(factory = IntBuilderDsl()).between(3, 3)()
        assertEquals(3, result.size)
    }

    @Test
    fun previous() {
        val faker = mock<Faker>()
        whenever(faker.int(0, 10)).thenReturn(3)
        val factory = IntBuilderDsl().constant(0)
        val result = ListBuilderDsl(factory, faker).previous { constant(it + 1) }()
        assertEquals(listOf(0, 1, 2), result)
    }
}
