package com.github.stefankoppier.builder.dsl.containers

import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker

/**
 * DSL for building [List] objects using the given [Faker] and element factory [B].
 *
 * For example:
 * ```kotlin
 * ListBuilder(IntBuilder().constant(1))()
 * ```
 * will result in a list of random size between `0` and `10` consisting of `1`'s.
 */
class ListBuilder<E, B>(private val factory: B, private val faker: Faker = Faker()) : BuilderDsl<List<E>> where
B : BuilderDsl<E> {

    private var constant: List<E>? = null

    private var min: Int = 0

    private var max: Int = 10

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [List].
     */
    override operator fun invoke(): List<E> {
        val size = 0 until faker.int(min, max)
        return (size - 1).fold(mutableListOf(factory())) { acc, _ -> acc with this.factory.invoke() }
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: List<E>): ListBuilder<E, B> {
        this.constant = value
        return this
    }

    /**
     * Instruct the builder to generate a list of size between [min] and [max].
     *
     * @param min The (inclusive) minimum value.
     * @param max The (exclusive) maximum value.
     *
     * @throws IllegalArgumentException When [min] is negative, [max] is negative, or [min] is less than [max].
     * @return The DSL itself.
     */
    fun between(min: Int, max: Int): ListBuilder<E, B> {
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    /**
     * Instruct the builder to generate a list of size greater or equal to [max].
     *
     * @param min The (inclusive) minimum value.
     *
     * @throws IllegalArgumentException When [min] is negative.
     * @return The DSL itself.
     */
    fun min(min: Int): ListBuilder<E, B> {
        require(min >= 0) { "min must be positive, instead '$min' was given" }

        this.min = min
        return this
    }

    /**
     * Instruct the builder to generate a list of size less than [max].
     *
     * @param max The (exclusive) maximum value.
     *
     * @throws IllegalArgumentException When [max] is negative.
     * @return The DSL itself.
     */
    fun max(max: Int): ListBuilder<E, B> {
        require(max >= 0) { "max must be positive, instead '$max' was given" }

        this.max = max
        return this
    }
}

private infix fun <E> MutableList<E>.with(element: E): MutableList<E> {
    this.add(element)
    return this
}
