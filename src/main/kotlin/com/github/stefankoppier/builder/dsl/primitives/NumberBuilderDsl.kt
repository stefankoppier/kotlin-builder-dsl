package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker

@Suppress("UNCHECKED_CAST")
abstract class NumberBuilderDsl<T : Number, B : NumberBuilderDsl<T, B>>(protected val faker: Faker = Faker()) :
    BuilderDsl<T> {

    private var constant: T? = null

    private var min: T? = null

    private var max: T? = null

    override fun invoke(): T {
        return constant ?: random(min, max)
    }

    fun constant(value: T): B {
        this.constant = value
        return this as B
    }

    /**
     * Instruct the builder to generate a value between [min] and [max].
     *
     * @param min The (inclusive) minimum value.
     * @param max The (exclusive) maximum value.
     *
     * @throws IllegalArgumentException When [min] is negative, [max] is negative, or [min] is less than [max].
     * @return The DSL itself.
     */
    fun between(min: T, max: T): B {
        require(min.toDouble() <= max.toDouble()) {
            "min must be less than or equal to max, instead min was '$min' and max was '$max'"
        }

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
    fun min(min: T): B {
        require(min.toInt() >= 0) { "min must be positive, instead '$min' was given" }

        this.min = min
        return this as B
    }

    /**
     * Instruct the builder to generate a value less than [max].
     *
     * @param max The (exclusive) maximum value.
     *
     * @throws IllegalArgumentException When [max] is negative.
     * @return The DSL itself.
     */
    fun max(max: T): B {
        require(max.toInt() > 0) { "max must be positive, instead '$max' was given" }

        this.max = max
        return this as B
    }

    protected abstract fun random(min: T?, max: T?): T
}
