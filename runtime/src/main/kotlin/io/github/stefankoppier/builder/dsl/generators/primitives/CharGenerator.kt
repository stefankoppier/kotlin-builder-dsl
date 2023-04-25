package io.github.stefankoppier.builder.dsl.generators.primitives

import io.github.stefankoppier.builder.dsl.Faker
import io.github.stefankoppier.builder.dsl.generators.GeneratorDsl

/** DSL for building [Char] objects using the given [Faker]. */
class CharGenerator(private val faker: Faker = Faker()) : GeneratorDsl<Char> {

    private var constant: Char? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [Char].
     */
    override operator fun invoke(): Char {
        return constant ?: faker.char()
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     * @return The DSL itself.
     */
    fun constant(value: Char): CharGenerator {
        constant = value
        return this
    }
}

/**
 * Generate a [Char].
 *
 * For example: `Char.of { constant(true) }`
 *
 * @param transform The instructions.
 * @return A new [Char].
 */
fun Char.Companion.of(transform: CharGenerator.() -> CharGenerator = { CharGenerator() }): Char {
    return transform(CharGenerator())()
}
