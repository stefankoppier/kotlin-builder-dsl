package io.github.stefankoppier.builder.dsl.generators.primitives

import io.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Short] objects using the given [Faker]. */
class ShortGenerator(faker: Faker = Faker()) : NumberGenerator<Short, ShortGenerator>(faker) {

    override fun random(min: Short?, max: Short?): Short {
        return faker.short(min ?: Short.MIN_VALUE, max ?: Short.MAX_VALUE)
    }
}

/**
 * Generate a [Short].
 *
 * For example: `Short.of { constant(1) }`
 *
 * @param transform The instructions.
 * @return A new [Short].
 */
fun Short.Companion.of(transform: ShortGenerator.() -> ShortGenerator = { ShortGenerator() }): Short {
    return transform(ShortGenerator())()
}
