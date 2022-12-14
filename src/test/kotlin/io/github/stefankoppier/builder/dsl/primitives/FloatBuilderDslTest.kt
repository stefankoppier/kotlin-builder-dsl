package io.github.stefankoppier.builder.dsl.primitives

import io.github.stefankoppier.builder.dsl.Faker
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FloatBuilderDslTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.float()).thenReturn(1.0f)
        assertEquals(1.0f, FloatBuilder(faker)())
    }

    @Test
    fun constant() {
        assertEquals(2.0f, FloatBuilder().constant(2.0f)())
    }

    @Test
    fun of() {
        assertEquals(3.0f, Float.of { constant(3.0f) })
    }
}
