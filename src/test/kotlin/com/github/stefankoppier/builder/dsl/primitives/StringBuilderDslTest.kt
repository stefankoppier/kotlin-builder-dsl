package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StringBuilderDslTest {

    @Test
    fun random() {
        val faker = Mockito.spy(Faker::class.java)
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
        assertTrue { StringBuilderDsl().format(regex)().matches(Regex(regex))  }
    }

    @Test
    fun `between 1 and 10`() {
        assertTrue { (0..10).contains((String.of { between(1, 10)}).length) }
    }

    @Test
    fun of() {
        assertEquals("3", String.of { constant("3") })
    }
}