package com.github.stefankoppier.builder.dsl

import java.math.BigDecimal

/** DSL for building [BigDecimal] objects using the given [Faker]. */
class BigDecimalBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<BigDecimal> {

    private var constant: BigDecimal? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [BigDecimal].
     */
    override fun invoke(): BigDecimal {
        return constant ?: BigDecimal.valueOf(faker.double())
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: BigDecimal): BigDecimalBuilderDsl {
        constant = value
        return this
    }
}
