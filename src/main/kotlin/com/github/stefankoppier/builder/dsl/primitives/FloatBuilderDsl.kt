package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Float] objects using the given [Faker]. */
class FloatBuilderDsl(faker: Faker = Faker()) : NumberBuilderDsl<Float, FloatBuilderDsl>(faker) {

    override fun random(min: Float?, max: Float?): Float {
        return faker.float(min ?: Float.MIN_VALUE, max ?: Float.MAX_VALUE)
    }
}

/**
 * Generate a [Float].
 *
 * For example: `Float.of { constant(1.0f) }`
 *
 * @param transform The instructions.
 *
 * @return A new [Float].
 */
fun Float.Companion.of(transform: FloatBuilderDsl.() -> FloatBuilderDsl = { FloatBuilderDsl() }): Float {
    return transform(FloatBuilderDsl())()
}
