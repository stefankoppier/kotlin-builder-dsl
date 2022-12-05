package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IntBuilderDslTest {

    @Test
    fun random() {
        val faker = Mockito.spy(Faker::class.java)
        whenever(faker.nextInt()).thenReturn(1)
        assertEquals(1, IntBuilderDsl(faker)())
    }

    @Test
    fun constant() {
        assertEquals(2, IntBuilderDsl().constant(2)())
    }

    @Test
    fun `between 1 and 10`() {
        assertTrue { (0..10).contains((Int.of { between(1, 10)})) }
    }

    @Test
    fun of() {
        assertEquals(3, Int.of { constant(3) })
    }
}