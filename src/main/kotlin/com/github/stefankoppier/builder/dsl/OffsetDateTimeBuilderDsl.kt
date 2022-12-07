package com.github.stefankoppier.builder.dsl

import java.time.OffsetDateTime

class OffsetDateTimeBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<OffsetDateTime> {

    private var constant: OffsetDateTime? = null

    override fun invoke(): OffsetDateTime {
        return constant ?: faker.offsetDateTime()
    }

    fun constant(value: OffsetDateTime): OffsetDateTimeBuilderDsl {
        this.constant = value
        return this
    }
}
