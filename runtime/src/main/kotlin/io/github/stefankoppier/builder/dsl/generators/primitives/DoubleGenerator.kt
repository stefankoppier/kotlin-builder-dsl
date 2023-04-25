package io.github.stefankoppier.builder.dsl.generators.primitives

import io.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Double] objects using the given [Faker]. */
class DoubleGenerator(faker: Faker = Faker()) : NumberGenerator<Double, DoubleGenerator>(faker) {

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
fun Double.Companion.of(transform: DoubleGenerator.() -> DoubleGenerator = { DoubleGenerator() }): Double {
    return transform(DoubleGenerator())()
}
