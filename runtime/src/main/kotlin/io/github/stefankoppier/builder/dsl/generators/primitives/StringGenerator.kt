package io.github.stefankoppier.builder.dsl.generators.primitives

import com.github.curiousoddman.rgxgen.RgxGen
import io.github.stefankoppier.builder.dsl.BuilderDsl
import io.github.stefankoppier.builder.dsl.Faker

/** DSL for building [String] objects using the given [Faker]. */
class StringGenerator(private val faker: Faker = Faker()) : BuilderDsl<String> {

    private var constant: String? = null

    private var format: String? = null

    private var min = 0

    private var max = 24

    /**
     * Generates the object according to the provided instructions.
     *
     * @throws IllegalArgumentException When [format] is not a valid pattern.
     * @return A new [String].
     */
    override operator fun invoke(): String {
        if (constant != null) {
            return constant!!
        }
        if (format != null) {
            try {
                return RgxGen(format).generate()
            } catch (e: Exception) {
                throw IllegalArgumentException("Failed to generate a value from '$format'.", e)
            }
        }
        return faker.string(min, max)
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: String): StringGenerator {
        constant = value
        format = null
        return this
    }

    /**
     * Instruct the builder to generate a random value that satisfied [pattern].
     *
     * When the format is not valid, a random [String] will be generated.
     *
     * @param pattern The pattern to satisfy.
     *
     * @return The DSL itself.
     */
    fun format(pattern: String): StringGenerator {
        format = pattern
        constant = null
        return this
    }

    /**
     * Instruct the builder to generate a value of length between [min] and [max].
     *
     * @param min The (inclusive) minimum value.
     * @param max The (exclusive) maximum value.
     *
     * @throws IllegalArgumentException When [min] is negative, [max] is negative, or [min] is less than [max].
     * @return The DSL itself.
     */
    fun between(min: Int, max: Int): StringGenerator {
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    /**
     * Instruct the builder to generate a value of length greater or equal to [max].
     *
     * @param max The (inclusive) minimum value.
     *
     * @throws IllegalArgumentException When [min] is negative.
     * @return The DSL itself.
     */
    fun min(min: Int): StringGenerator {
        require(min >= 0) { "min must be positive, instead '$min' was given" }

        this.min = min
        return this
    }

    /**
     * Instruct the builder to generate a value of length less than [max].
     *
     * @param max The (exclusive) maximum value.
     *
     * @throws IllegalArgumentException When [max] is negative.
     * @return The DSL itself.
     */
    fun max(max: Int): StringGenerator {
        require(max >= 0) { "max must be positive, instead '$max' was given" }

        this.max = max
        return this
    }
}

/**
 * Generate a [String].
 *
 * For example: `String.of { constant("value") }`
 *
 * @param transform The instructions.
 *
 * @return A new [String].
 */
fun String.Companion.of(transform: StringGenerator.() -> StringGenerator = { StringGenerator() }): String {
    return transform(StringGenerator())()
}
