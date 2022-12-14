package io.github.stefankoppier.builder.dsl.primitives

import io.github.stefankoppier.builder.dsl.BuilderDsl
import io.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Boolean] objects using the given [Faker]. */
class BooleanBuilder(private val faker: Faker = Faker()) : BuilderDsl<Boolean> {

    private var constant: Boolean? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [Boolean].
     */
    override operator fun invoke(): Boolean {
        return constant ?: faker.boolean()
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: Boolean): BooleanBuilder {
        constant = value
        return this
    }
}

/**
 * Generate a [Boolean].
 *
 * For example: `Boolean.of { constant(true) }`
 *
 * @param transform The instructions.
 *
 * @return A new [Boolean].
 */
fun Boolean.Companion.of(transform: BooleanBuilder.() -> BooleanBuilder = { BooleanBuilder() }): Boolean {
    return transform(BooleanBuilder())()
}
