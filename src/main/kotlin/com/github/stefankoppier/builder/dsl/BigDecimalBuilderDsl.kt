package com.github.stefankoppier.builder.dsl

import java.math.BigDecimal

class BigDecimalBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<BigDecimal> {

    private var constant: BigDecimal? = null

    override fun invoke(): BigDecimal {
        return constant ?: BigDecimal.valueOf(faker.nextDouble())
    }

    fun constant(value: BigDecimal): BigDecimalBuilderDsl {
        constant = value
        return this
    }
}