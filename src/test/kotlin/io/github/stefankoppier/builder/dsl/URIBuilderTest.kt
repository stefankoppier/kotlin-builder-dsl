package io.github.stefankoppier.builder.dsl

import java.net.URI
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

class URIBuilderTest {

    @Test
    fun random() {
        assertTrue { URIBuilder()().toString().matches(Regex("https://[az]+.com")) }
    }

    @Test
    fun constant() {
        assertEquals(URI.create("https://localhost"), URIBuilder().constant(URI.create("https://localhost"))())
    }
}
