package io.github.stefankoppier.builder.dsl.generators

import io.github.stefankoppier.builder.dsl.Faker
import java.time.LocalDate
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LocalDateGeneratorTest {

    @Test
    fun random() {
        val faker = mock<Faker>()
        whenever(faker.localDate()).thenReturn(LocalDate.EPOCH)
        assertEquals(LocalDate.EPOCH, LocalDateGenerator(faker)())
    }

    @Test
    fun constant() {
        assertEquals(LocalDate.EPOCH, LocalDateGenerator().constant(LocalDate.EPOCH)())
    }
}
