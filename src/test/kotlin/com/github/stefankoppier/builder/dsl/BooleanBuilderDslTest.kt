package com.github.stefankoppier.builder.dsl

import org.mockito.Mockito.spy
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanBuilderDslTest {

    @Test
    fun `random`() {
        val faker = spy(Faker::class.java)
        whenever(faker.nextBoolean()).thenReturn(false)
        assertFalse { BooleanBuilderDsl(faker)() }
    }

    @Test
    fun `constant`() {
        assertTrue { BooleanBuilderDsl().constant(true)() }
    }
}