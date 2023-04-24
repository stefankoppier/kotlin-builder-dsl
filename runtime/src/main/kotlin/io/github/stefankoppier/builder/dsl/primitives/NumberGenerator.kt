package io.github.stefankoppier.builder.dsl.primitives

import io.github.stefankoppier.builder.dsl.BuilderDsl
import io.github.stefankoppier.builder.dsl.Faker

@Suppress("UNCHECKED_CAST")
abstract class NumberGenerator<T : Number, B : NumberGenerator<T, B>>(protected val faker: Faker = Faker()) :
    BuilderDsl<T> {

    private var constant: T? = null

    private var min: T? = null

    private var max: T? = null

    override operator fun invoke(): T {
        return constant ?: random(min, max)
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
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
     * @throws IllegalArgumentException When [min] is less than [max].
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
     * @return The DSL itself.
     */
    fun min(min: T): B {
        this.min = min
        return this as B
    }

    /**
     * Instruct the builder to generate a value less than [max].
     *
     * @param max The (exclusive) maximum value.
     *
     * @return The DSL itself.
     */
    fun max(max: T): B {
        this.max = max
        return this as B
    }

    protected abstract fun random(min: T?, max: T?): T
}
