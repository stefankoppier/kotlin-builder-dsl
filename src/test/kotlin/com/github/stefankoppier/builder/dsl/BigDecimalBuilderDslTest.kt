package com.github.stefankoppier.builder.dsl

import java.math.BigDecimal
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BigDecimalBuilderDslTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.nextDouble()).thenReturn(1.0)
        assertEquals(BigDecimal.valueOf(1.0), BigDecimalBuilderDsl(faker)())
    }

    @Test
    fun constant() {
        assertEquals(BigDecimal.valueOf(2.0), BigDecimalBuilderDsl().constant(BigDecimal.valueOf(2.0))())
    }
}
