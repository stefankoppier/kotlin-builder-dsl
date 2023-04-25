package io.github.stefankoppier.builder.dsl.containers

import io.github.stefankoppier.builder.dsl.Faker
import io.github.stefankoppier.builder.dsl.generators.primitives.IntGenerator
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ListBuilderTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.int(0, 10)).thenReturn(2)
        val factory = IntGenerator(faker).constant(1)
        val result = ListBuilder(factory, faker)()
        assertEquals(listOf(1, 1), result)
    }

    @Test
    fun between() {
        val result = ListBuilder(IntGenerator()).between(3, 3)()
        assertEquals(3, result.size)
    }

    @Test
    fun constant() {
        val result = ListBuilder(IntGenerator()).constant(listOf(1, 2, 3))()
        assertEquals(listOf(1, 2, 3), result)
    }
}
