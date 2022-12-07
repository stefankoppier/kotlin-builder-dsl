package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Int] objects using the given [Faker]. */
class IntBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<Int> {

    private var constant: Int? = null

    private var min = Int.MIN_VALUE

    private var max = Int.MAX_VALUE

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [Int].
     */
    override fun invoke(): Int {
        return constant ?: faker.int(min, max)
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: Int): IntBuilderDsl {
        constant = value
        return this
    }

    /**
     * Instruct the builder to generate a value between [min] and [max].
     *
     * @param min The (inclusive) minimum value.
     * @param max The (exclusive) maximum value.
     *
     * @throws IllegalArgumentException When [min] is less than [max].
     * @throws IllegalArgumentException When [min] is negative.
     * @throws IllegalArgumentException When [max] is negative.
     * @return The DSL itself.
     */
    fun between(min: Int, max: Int): IntBuilderDsl {
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    /**
     * Instruct the builder to generate a value greater or equal to [min].
     *
     * @param min The (inclusive) minimum value.
     *
     * @throws IllegalArgumentException When [min] is negative.
     * @return The DSL itself.
     */
    fun min(min: Int): IntBuilderDsl {
        require(min >= 0) { "min must be positive, instead '$min' was given" }

        this.min = min
        return this
    }

    /**
     * Instruct the builder to generate a value less than [max].
     *
     * @param max The (exclusive) maximum value.
     *
     * @throws IllegalArgumentException When [max] is negative.
     * @return The DSL itself.
     */
    fun max(max: Int): IntBuilderDsl {
        require(max >= 0) { "max must be positive, instead '$max' was given" }

        this.max = max
        return this
    }
}

/**
 * Generate a [Int].
 *
 * For example: `Int.of { constant(1) }`
 *
 * @param transform The instructions.
 *
 * @return A new [Int].
 */
fun Int.Companion.of(transform: IntBuilderDsl.() -> IntBuilderDsl = { IntBuilderDsl() }): Int {
    return transform(IntBuilderDsl())()
}
