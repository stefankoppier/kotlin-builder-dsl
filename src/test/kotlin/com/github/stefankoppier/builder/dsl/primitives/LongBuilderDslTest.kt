package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LongBuilderDslTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.nextLong()).thenReturn(1)
        assertEquals(1, LongBuilderDsl(faker)())
    }

    @Test
    fun constant() {
        assertEquals(2, LongBuilderDsl().constant(2)())
    }

    @Test
    fun `between 1 and 10`() {
        assertTrue { Long.of { between(1, 10) } in (0..10) }
    }

    @Test
    fun of() {
        assertEquals(3, Long.of { constant(3) })
    }
}
