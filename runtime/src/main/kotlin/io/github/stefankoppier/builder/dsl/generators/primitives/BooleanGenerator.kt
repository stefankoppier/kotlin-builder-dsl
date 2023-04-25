package io.github.stefankoppier.builder.dsl.generators.primitives

import io.github.stefankoppier.builder.dsl.Faker
import io.github.stefankoppier.builder.dsl.generators.GeneratorDsl

/** DSL for building [Boolean] objects using the given [Faker]. */
class BooleanGenerator(private val faker: Faker = Faker()) : GeneratorDsl<Boolean> {

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
    fun constant(value: Boolean): BooleanGenerator {
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
fun Boolean.Companion.of(transform: BooleanGenerator.() -> BooleanGenerator = { BooleanGenerator() }): Boolean {
    return transform(BooleanGenerator())()
}
