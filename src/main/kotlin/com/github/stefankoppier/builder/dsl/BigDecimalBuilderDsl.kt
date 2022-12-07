package com.github.stefankoppier.builder.dsl

import com.github.stefankoppier.builder.dsl.primitives.NumberBuilderDsl
import java.math.BigDecimal

/** DSL for building [BigDecimal] objects using the given [Faker]. */
class BigDecimalBuilderDsl(faker: Faker = Faker()) : NumberBuilderDsl<BigDecimal, BigDecimalBuilderDsl>(faker) {

    override fun random(min: BigDecimal?, max: BigDecimal?): BigDecimal {
        return BigDecimal.valueOf(
            faker.double(min?.toDouble() ?: Double.MIN_VALUE, max?.toDouble() ?: Double.MAX_VALUE))
    }
}
