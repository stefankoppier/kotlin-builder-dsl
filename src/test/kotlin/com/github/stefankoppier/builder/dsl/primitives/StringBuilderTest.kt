package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class StringBuilderTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.string()).thenReturn("1")
        assertEquals("1", StringBuilder(faker)())
    }

    @Test
    fun constant() {
        assertEquals("2", StringBuilder().constant("2")())
    }

    @Test
    fun `format of valid pattern`() {
        val regex = "[aAzZ]+"
        assertTrue { StringBuilder().format(regex)().matches(Regex(regex)) }
    }

    @Test
    fun `format of invalid pattern`() {
        assertThrows<IllegalArgumentException> { StringBuilder().format("a{-+1}")() }
    }

    @Test
    fun `between 1 and 10`() {
        assertTrue { String.of { between(1, 10) }.length in (0..10) }
    }

    @Test
    fun `between 10 and 1`() {
        assertThrows<IllegalArgumentException> { String.of { between(10, 1) } }
    }

    @Test
    fun `min of negative number`() {
        assertThrows<IllegalArgumentException> { String.of { min(-1) } }
    }

    @Test
    fun `max of negative number`() {
        assertThrows<IllegalArgumentException> { String.of { max(-1) } }
    }

    @Test
    fun of() {
        assertEquals("3", String.of { constant("3") })
    }
}
