package io.github.stefankoppier.builder.dsl.generators

import io.github.stefankoppier.builder.dsl.Faker
import io.github.stefankoppier.builder.dsl.generators.primitives.NumberGenerator
import java.math.BigDecimal

/** DSL for building [BigDecimal] objects using the given [Faker]. */
class BigDecimalGenerator(faker: Faker = Faker()) : NumberGenerator<BigDecimal, BigDecimalGenerator>(faker) {

    override fun random(min: BigDecimal?, max: BigDecimal?): BigDecimal {
        return BigDecimal.valueOf(
            faker.double(min?.toDouble() ?: Double.MIN_VALUE, max?.toDouble() ?: Double.MAX_VALUE))
    }
}