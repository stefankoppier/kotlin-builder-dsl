package io.github.stefankoppier.builder.dsl.primitives

import io.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Double] objects using the given [Faker]. */
class DoubleBuilder(faker: Faker = Faker()) : NumberBuilder<Double, DoubleBuilder>(faker) {

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
fun Double.Companion.of(transform: DoubleBuilder.() -> DoubleBuilder = { DoubleBuilder() }): Double {
    return transform(DoubleBuilder())()
}
