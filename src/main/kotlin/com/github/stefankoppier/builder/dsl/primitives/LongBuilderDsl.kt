package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Long] objects using the given [Faker]. */
class LongBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<Long> {

    private var constant: Long? = null

    private var min = Long.MIN_VALUE

    private var max = Long.MAX_VALUE

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [Long].
     */
    override fun invoke(): Long {
        return constant ?: faker.long(min, max)
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: Long): LongBuilderDsl {
        constant = value
        return this
    }

    fun between(min: Long, max: Long): LongBuilderDsl {
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    fun min(min: Long): LongBuilderDsl {
        require(min >= 0) { "min must be positive, instead '$min' was given" }

        this.min = min
        return this
    }

    fun max(max: Long): LongBuilderDsl {
        require(max >= 0) { "max must be positive, instead '$max' was given" }

        this.max = max
        return this
    }
}

/**
 * Generate a [Long].
 *
 * For example: `Long.of { constant(1L) }`
 *
 * @param transform The instructions.
 *
 * @return A new [Long].
 */
fun Long.Companion.of(transform: LongBuilderDsl.() -> LongBuilderDsl = { LongBuilderDsl() }): Long {
    return transform(LongBuilderDsl())()
}
