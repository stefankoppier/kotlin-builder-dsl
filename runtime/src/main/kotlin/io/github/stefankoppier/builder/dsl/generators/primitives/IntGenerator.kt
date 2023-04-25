package io.github.stefankoppier.builder.dsl.generators.primitives

import io.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Int] objects using the given [Faker]. */
class IntGenerator(faker: Faker = Faker()) : NumberGenerator<Int, IntGenerator>(faker) {

    override fun random(min: Int?, max: Int?): Int {
        return faker.int(min ?: Int.MIN_VALUE, max ?: Int.MAX_VALUE)
    }
}

/**
 * Generate a [Int].
 *
 * For example: `Int.of { constant(1) }`
 *
 * @param transform The instructions.
 * @return A new [Int].
 */
fun Int.Companion.of(transform: IntGenerator.() -> IntGenerator = { IntGenerator() }): Int {
    return transform(IntGenerator())()
}
