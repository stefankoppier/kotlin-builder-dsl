package io.github.stefankoppier.builder.dsl.generators.primitives

import io.github.stefankoppier.builder.dsl.Faker
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ByteGeneratorTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.byte()).thenReturn(1)
        assertEquals(1, ByteGenerator(faker)())
    }

    @Test
    fun constant() {
        assertEquals(2, ByteGenerator().constant(2)())
    }

    @Test
    fun `between 1 and 10`() {
        assertTrue { Byte.of { between(1, 10) } in (0..10) }
    }

    @Test
    fun `between 10 and 1`() {
        assertThrows<IllegalArgumentException> { Byte.of { between(10, 1) } }
    }

    @Test
    fun of() {
        assertEquals(3, Byte.of { constant(3) })
    }
}
