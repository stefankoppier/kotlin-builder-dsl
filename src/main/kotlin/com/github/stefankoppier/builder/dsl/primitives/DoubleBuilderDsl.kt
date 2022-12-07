package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Double] objects using the given [Faker]. */
class DoubleBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<Double> {

    private var constant: Double? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [Double].
     */
    override fun invoke(): Double {
        return constant ?: faker.double()
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: Double): DoubleBuilderDsl {
        constant = value
        return this
    }
}

/**
 * Generate a [Double].
 *
 * For example: `Double.of { constant(1.0) }`
 *
 * @param transform The instructions.
 *
 * @return A new [Double].
 */
fun Double.Companion.of(transform: DoubleBuilderDsl.() -> DoubleBuilderDsl = { DoubleBuilderDsl() }): Double {
    return transform(DoubleBuilderDsl())()
}
