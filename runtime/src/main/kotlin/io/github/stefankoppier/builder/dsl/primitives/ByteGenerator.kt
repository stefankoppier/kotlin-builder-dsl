package io.github.stefankoppier.builder.dsl.primitives

import io.github.stefankoppier.builder.dsl.Faker

/** DSL for building [Byte] objects using the given [Faker]. */
class ByteGenerator(faker: Faker = Faker()) : NumberGenerator<Byte, ByteGenerator>(faker) {

    override fun random(min: Byte?, max: Byte?): Byte {
        return faker.byte(min ?: Byte.MIN_VALUE, max ?: Byte.MAX_VALUE)
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
fun Byte.Companion.of(transform: ByteGenerator.() -> ByteGenerator = { ByteGenerator() }): Byte {
    return transform(ByteGenerator())()
}
