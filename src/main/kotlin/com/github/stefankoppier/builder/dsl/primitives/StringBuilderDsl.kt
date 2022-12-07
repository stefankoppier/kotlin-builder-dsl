package com.github.stefankoppier.builder.dsl.primitives

import com.github.curiousoddman.rgxgen.RgxGen
import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker
import org.slf4j.LoggerFactory

/** DSL for building [String] objects using the given [Faker]. */
class StringBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<String> {

    private val logger = LoggerFactory.getLogger(StringBuilderDsl::class.java)

    private var constant: String? = null

    private var format: String? = null

    private var min = 0

    private var max = 24

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [String].
     */
    override fun invoke(): String {
        if (constant != null) {
            return constant!!
        }
        if (format != null) {
            try {
                return RgxGen(format).generate()
            } catch (e: Exception) {
                val message = "Failed to generate regex from '$format' falling back to a random value."
                logger.error(message, e)
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
    fun constant(value: String): StringBuilderDsl {
        constant = value
        format = null
        return this
    }

    /**
     * Instruct the builder to generate a random value that satisfied [pattern].
     *
     * @param pattern The pattern to satisfy.
     *
     * @return The DSL itself.
     */
    fun format(pattern: String): StringBuilderDsl {
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
    fun between(min: Int, max: Int): StringBuilderDsl {
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    /**
     * Instruct the builder to generate a value greater or equal to [max].
     *
     * @param max The (inclusive) minimum value.
     *
     * @throws IllegalArgumentException When [min] is negative.
     * @return The DSL itself.
     */
    fun min(min: Int): StringBuilderDsl {
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
    fun max(max: Int): StringBuilderDsl {
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
fun String.Companion.of(transform: StringBuilderDsl.() -> StringBuilderDsl = { StringBuilderDsl() }): String {
    return transform(StringBuilderDsl())()
}
