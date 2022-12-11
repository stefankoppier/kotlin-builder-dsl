package io.github.stefankoppier.builder.dsl.primitives

import io.github.stefankoppier.builder.dsl.Faker
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BooleanBuilderTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.boolean()).thenReturn(false)
        assertFalse { BooleanBuilder(faker)() }
    }

    @Test
    fun constant() {
        assertTrue { BooleanBuilder().constant(true)() }
    }

    @Test
    fun of() {
        assertTrue { Boolean.of { constant(true) } }
    }
}
