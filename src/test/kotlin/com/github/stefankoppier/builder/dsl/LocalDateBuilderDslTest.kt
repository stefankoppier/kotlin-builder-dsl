package com.github.stefankoppier.builder.dsl

import java.time.LocalDate
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LocalDateBuilderDslTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.nextLocalDate()).thenReturn(LocalDate.EPOCH)
        assertEquals(LocalDate.EPOCH, LocalDateBuilderDsl(faker)())
    }

    @Test
    fun constant() {
        assertEquals(LocalDate.EPOCH, LocalDateBuilderDsl().constant(LocalDate.EPOCH)())
    }
}
