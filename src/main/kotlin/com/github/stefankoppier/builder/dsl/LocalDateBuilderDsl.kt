package com.github.stefankoppier.builder.dsl

import java.time.LocalDate

class LocalDateBuilderDsl : BuilderDsl<LocalDate> {

    private var constant: LocalDate? = null

    override fun invoke(): LocalDate {
        return constant ?: Faker().nextLocalDate()
    }

    fun constant(value: LocalDate): LocalDateBuilderDsl {
        this.constant = value
        return this
    }
}