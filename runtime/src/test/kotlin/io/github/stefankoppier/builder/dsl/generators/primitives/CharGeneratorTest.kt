package io.github.stefankoppier.builder.dsl.generators.primitives

import io.github.stefankoppier.builder.dsl.Faker
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CharGeneratorTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.char()).thenReturn('a')
        assertEquals('a', CharGenerator(faker)())
    }

    @Test
    fun constant() {
        assertEquals('b', CharGenerator().constant('b')())
    }
}
