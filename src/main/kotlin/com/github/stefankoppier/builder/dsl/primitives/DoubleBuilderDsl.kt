package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Double] objects using the given [Faker]. */
class DoubleBuilderDsl(faker: Faker = Faker()) : NumberBuilderDsl<Double, DoubleBuilderDsl>(faker) {
    override fun random(min: Double?, max: Double?): Double {
        return faker.double(min ?: Double.MIN_VALUE, max ?: Double.MAX_VALUE)
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
