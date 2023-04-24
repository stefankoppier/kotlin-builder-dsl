package io.github.stefankoppier.builder.dsl

import java.time.LocalDate

/** DSL for building [LocalDate] objects using the given [Faker]. */
class LocalDateBuilder(private val faker: Faker = Faker()) : BuilderDsl<LocalDate> {

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
     *
     * @return The DSL itself.
     */
    fun constant(value: LocalDate): LocalDateBuilder {
        this.constant = value
        return this
    }
}
