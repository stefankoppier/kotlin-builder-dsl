package io.github.stefankoppier.builder.dsl.generators

import java.net.URI
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

class URIGeneratorTest {

    @Test
    fun random() {
        assertTrue { URIGenerator()().toString().matches(Regex("https://[az]+.com")) }
    }

    @Test
    fun constant() {
        assertEquals(URI.create("https://localhost"), URIGenerator().constant(URI.create("https://localhost"))())
    }
}
