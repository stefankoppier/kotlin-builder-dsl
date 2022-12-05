package com.github.stefankoppier.builder.dsl

import java.time.OffsetDateTime

class OffsetDateTimeBuilderDsl : BuilderDsl<OffsetDateTime> {

    private var constant: OffsetDateTime? = null

    override fun invoke(): OffsetDateTime {
        return constant ?: Faker().nextOffsetDateTime()
    }

    fun constant(value: OffsetDateTime): OffsetDateTimeBuilderDsl {
        this.constant = value
        return this
    }
}
