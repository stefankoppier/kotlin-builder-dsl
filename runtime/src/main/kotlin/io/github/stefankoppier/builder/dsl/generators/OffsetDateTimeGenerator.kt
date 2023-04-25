package io.github.stefankoppier.builder.dsl.generators

import io.github.stefankoppier.builder.dsl.Faker
import java.time.OffsetDateTime

/** DSL for building [OffsetDateTime] objects using the given [Faker]. */
class OffsetDateTimeGenerator(private val faker: Faker = Faker()) : GeneratorDsl<OffsetDateTime> {

    private var constant: OffsetDateTime? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [OffsetDateTime].
     */
    override operator fun invoke(): OffsetDateTime {
        return constant ?: faker.offsetDateTime()
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: OffsetDateTime): OffsetDateTimeGenerator {
        this.constant = value
        return this
    }
}
