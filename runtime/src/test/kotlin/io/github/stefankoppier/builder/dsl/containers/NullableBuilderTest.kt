package io.github.stefankoppier.builder.dsl.containers

import io.github.stefankoppier.builder.dsl.Faker
import io.github.stefankoppier.builder.dsl.primitives.IntGenerator
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NullableBuilderTest {

    @Test
    fun `random null`() {
        val faker = mock<Faker>()
        whenever(faker.boolean()).thenReturn(false)
        assertEquals(null, NullableBuilder(IntGenerator().constant(0), faker)())
    }

    @Test
    fun `random instance`() {
        val faker = mock<Faker>()
        whenever(faker.boolean()).thenReturn(true)
        assertEquals(0, NullableBuilder(IntGenerator().constant(0), faker)())
    }

    @Test
    fun asNull() {
        assertEquals(null, NullableBuilder(IntGenerator().constant(0)).asNull()())
    }

    @Test
    fun asNonNull() {
        assertEquals(0, NullableBuilder(IntGenerator().constant(0)).asNonNull()())
    }
}
