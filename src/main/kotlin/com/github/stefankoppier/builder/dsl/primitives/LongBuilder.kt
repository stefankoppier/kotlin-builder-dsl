package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Long] objects using the given [Faker]. */
class LongBuilder(faker: Faker = Faker()) : NumberBuilder<Long, LongBuilder>(faker) {

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
fun Long.Companion.of(transform: LongBuilder.() -> LongBuilder = { LongBuilder() }): Long {
    return transform(LongBuilder())()
}
