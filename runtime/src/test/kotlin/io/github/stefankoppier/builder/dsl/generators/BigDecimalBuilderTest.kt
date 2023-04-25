package io.github.stefankoppier.builder.dsl.generators

import io.github.stefankoppier.builder.dsl.Faker
import java.math.BigDecimal
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class BigDecimalBuilderTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.double()).thenReturn(1.0)
        assertEquals(BigDecimal.valueOf(1.0), BigDecimalGenerator(faker)())
    }

    @Test
    fun constant() {
        assertEquals(BigDecimal.valueOf(2.0), BigDecimalGenerator().constant(BigDecimal.valueOf(2.0))())
    }
}
