package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Float] objects using the given [Faker]. */
class FloatBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<Float> {

    private var constant: Float? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [Float].
     */
    override fun invoke(): Float {
        return constant ?: faker.float()
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: Float): FloatBuilderDsl {
        constant = value
        return this
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
