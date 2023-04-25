package io.github.stefankoppier.builder.dsl.generators

import io.github.stefankoppier.builder.dsl.Faker
import java.time.LocalDate

/** DSL for building [LocalDate] objects using the given [Faker]. */
class LocalDateGenerator(private val faker: Faker = Faker()) : GeneratorDsl<LocalDate> {

    private var constant: LocalDate? = null

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new [LocalDate].
     */
    override operator fun invoke(): LocalDate {
        return constant ?: faker.localDate()
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     * @return The DSL itself.
     */
    fun constant(value: LocalDate): LocalDateGenerator {
        this.constant = value
        return this
    }
}
