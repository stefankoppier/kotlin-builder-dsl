package io.github.stefankoppier.builder.dsl.primitives

import io.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Long] objects using the given [Faker]. */
class LongGenerator(faker: Faker = Faker()) : NumberGenerator<Long, LongGenerator>(faker) {

    override fun random(min: Long?, max: Long?): Long {
        return faker.long(min ?: Long.MIN_VALUE, max ?: Long.MAX_VALUE)
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
fun Long.Companion.of(transform: LongGenerator.() -> LongGenerator = { LongGenerator() }): Long {
    return transform(LongGenerator())()
}
