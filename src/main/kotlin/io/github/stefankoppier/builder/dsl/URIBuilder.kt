package io.github.stefankoppier.builder.dsl

import io.github.stefankoppier.builder.dsl.primitives.StringBuilder
import java.net.URI

/** DSL for building [URI] objects using the given [Faker]. */
class URIBuilder(private val faker: Faker = Faker()) : BuilderDsl<URI> {

    /** The protocol for which to generate an [URI] for. */
    var protocol = "https"
        private set

    private var constant: URI? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [URI].
     */
    override fun invoke(): URI {
        val host = StringBuilder(faker).format("[az]{1,15}")() + ".com"
        return constant ?: URI.create("$protocol://$host")
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: URI): URIBuilder {
        this.constant = value
        return this
    }

    /**
     * Instruct the builder to use a specific protocol when generating a random value.
     *
     * @param value The protocol for which to generate an [URI] for.
     *
     * @return The DSL itself.
     */
    fun protocol(value: String): URIBuilder {
        this.protocol = value
        return this
    }
}
