package io.github.stefankoppier.builder.dsl

import io.github.stefankoppier.builder.dsl.primitives.NumberBuilder
import java.math.BigDecimal

/** DSL for building [BigDecimal] objects using the given [Faker]. */
class BigDecimalBuilder(faker: Faker = Faker()) : NumberBuilder<BigDecimal, BigDecimalBuilder>(faker) {

    override fun random(min: BigDecimal?, max: BigDecimal?): BigDecimal {
        return BigDecimal.valueOf(
            faker.double(min?.toDouble() ?: Double.MIN_VALUE, max?.toDouble() ?: Double.MAX_VALUE))
    }
}
