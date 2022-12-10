package com.github.stefankoppier.builder.dsl.containers

import com.github.stefankoppier.builder.dsl.Faker
import com.github.stefankoppier.builder.dsl.primitives.IntBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NullableBuilderTest {

    @Test
    fun `random null`() {
        val faker = mock<Faker>()
        whenever(faker.boolean()).thenReturn(false)
        assertEquals(null, NullableBuilder(IntBuilder().constant(0), faker)())
    }

    @Test
    fun `random instance`() {
        val faker = mock<Faker>()
        whenever(faker.boolean()).thenReturn(true)
        assertEquals(0, NullableBuilder(IntBuilder().constant(0), faker)())
    }

    @Test
    fun asNull() {
        assertEquals(null, NullableBuilder(IntBuilder().constant(0)).asNull()())
    }

    @Test
    fun asNonNull() {
        assertEquals(0, NullableBuilder(IntBuilder().constant(0)).asNonNull()())
    }
}
