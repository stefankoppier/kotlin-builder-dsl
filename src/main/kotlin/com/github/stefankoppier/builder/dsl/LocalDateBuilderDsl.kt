package com.github.stefankoppier.builder.dsl

import java.time.LocalDate

class LocalDateBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<LocalDate> {

    private var constant: LocalDate? = null

    override fun invoke(): LocalDate {
        return constant ?: faker.nextLocalDate()
    }

    fun constant(value: LocalDate): LocalDateBuilderDsl {
        this.constant = value
        return this
    }
}