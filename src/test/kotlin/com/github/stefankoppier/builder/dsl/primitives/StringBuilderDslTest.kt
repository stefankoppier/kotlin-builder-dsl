package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class StringBuilderDslTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.nextString()).thenReturn("1")
        assertEquals("1", StringBuilderDsl(faker)())
    }

    @Test
    fun constant() {
        assertEquals("2", StringBuilderDsl().constant("2")())
    }

    @Test
    fun format() {
        val regex = "[aAzZ]+"
        assertTrue { StringBuilderDsl().format(regex)().matches(Regex(regex)) }
    }

    @Test
    fun `between 1 and 10`() {
        assertTrue { String.of { between(1, 10) }.length in (0..10) }
    }

    @Test
    fun of() {
        assertEquals("3", String.of { constant("3") })
    }
}
