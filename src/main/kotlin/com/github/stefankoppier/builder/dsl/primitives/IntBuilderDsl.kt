package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Int] objects using the given [Faker]. */
class IntBuilderDsl(faker: Faker = Faker()) : NumberBuilderDsl<Int, IntBuilderDsl>(faker) {
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
 *
 * @return A new [Int].
 */
fun Int.Companion.of(transform: IntBuilderDsl.() -> IntBuilderDsl = { IntBuilderDsl() }): Int {
    return transform(IntBuilderDsl())()
}
