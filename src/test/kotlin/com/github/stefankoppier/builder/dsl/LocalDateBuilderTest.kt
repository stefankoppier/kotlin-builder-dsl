package com.github.stefankoppier.builder.dsl

import java.time.LocalDate
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LocalDateBuilderTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.localDate()).thenReturn(LocalDate.EPOCH)
        assertEquals(LocalDate.EPOCH, LocalDateBuilder(faker)())
    }

    @Test
    fun constant() {
        assertEquals(LocalDate.EPOCH, LocalDateBuilder().constant(LocalDate.EPOCH)())
    }
}
