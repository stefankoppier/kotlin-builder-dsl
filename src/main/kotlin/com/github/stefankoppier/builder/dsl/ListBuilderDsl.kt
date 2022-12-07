package com.github.stefankoppier.builder.dsl

/**
 * DSL for building [List] objects using the given [Faker] and element factory [B].
 *
 * For example:
 * ```kotlin
 * ListBuilderDsl(Int.of { constant(1) })
 * ```
 * will result in a list of random size between 0 and 10 consisting of 1's.
 */
class ListBuilderDsl<E, B : BuilderDsl<E>>(private val factory: B, private val faker: Faker = Faker()) :
    BuilderDsl<List<E>> {

    private var min: Int = 0

    private var max: Int = 10

    private var previous: (B.(E) -> B)? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [List].
     */
    override fun invoke(): List<E> {
        val size = 0 until faker.int(min, max)

        if (previous != null) {
            val first = factory()
            val previous = previous!!
            return (size - 1).fold(mutableListOf(first)) { acc, _ -> acc.with(factory.previous(acc.last())()) }
        }
        return size.map { factory() }
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
    fun between(min: Int, max: Int): ListBuilderDsl<E, B> {
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    /**
     * Instruct the builder to generate a list of size greater or equal to [max].
     *
     * @param max The (inclusive) minimum value.
     *
     * @throws IllegalArgumentException When [min] is negative.
     * @return The DSL itself.
     */
    fun min(min: Int): ListBuilderDsl<E, B> {
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
    fun max(max: Int): ListBuilderDsl<E, B> {
        require(max >= 0) { "max must be positive, instead '$max' was given" }

        this.max = max
        return this
    }

    /**
     * Instruct the builder to generate a list where each element depends on the previous. For example:
     * ```kotlin
     * ListBuilderDsl(Int.of { constant(1) })
     *      .previous { constant(it + 1) }
     * ```
     * will result in a list of size between 0 and 10 starting with the sequence `1,2,...,n`.
     *
     * @param transform The function which is applied to each element.
     *
     * @return The DSL itself.
     */
    fun previous(transform: B.(E) -> B): ListBuilderDsl<E, B> {
        this.previous = transform
        return this
    }
}

private fun <E> MutableList<E>.with(element: E): MutableList<E> {
    this.add(element)
    return this
}
