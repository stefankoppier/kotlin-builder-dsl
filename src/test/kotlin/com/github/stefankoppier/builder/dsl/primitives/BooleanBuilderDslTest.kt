package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BooleanBuilderDslTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.nextBoolean()).thenReturn(false)
        assertFalse { BooleanBuilderDsl(faker)() }
    }

    @Test
    fun constant() {
        assertTrue { BooleanBuilderDsl().constant(true)() }
    }
}
