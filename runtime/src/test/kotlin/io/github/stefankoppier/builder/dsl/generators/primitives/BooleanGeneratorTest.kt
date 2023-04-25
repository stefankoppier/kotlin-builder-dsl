package io.github.stefankoppier.builder.dsl.generators.primitives

import io.github.stefankoppier.builder.dsl.Faker
import io.github.stefankoppier.builder.dsl.generators.primitives.BooleanGenerator
import io.github.stefankoppier.builder.dsl.generators.primitives.of
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BooleanGeneratorTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.boolean()).thenReturn(false)
        assertFalse { BooleanGenerator(faker)() }
    }

    @Test
    fun constant() {
        assertTrue { BooleanGenerator().constant(true)() }
    }

    @Test
    fun of() {
        assertTrue { Boolean.of { constant(true) } }
    }
}
