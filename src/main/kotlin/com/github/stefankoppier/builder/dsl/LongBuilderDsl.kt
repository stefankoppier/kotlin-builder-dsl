package com.github.stefankoppier.builder.dsl

class LongBuilderDsl(private val faker: Faker = Faker()): BuilderDsl<Long> {

    private var constant: Long? = null

    private var min = Long.MIN_VALUE

    private var max = Long.MAX_VALUE

    override fun invoke(): Long {
        return constant ?: faker.nextLong(min, max)
    }

    fun constant(value: Long): LongBuilderDsl {
        constant = value
        return this
    }

    fun between(min: Long, max: Long): LongBuilderDsl {
        require(min >= 0) { "min must be positive, instead '$min' was given"}
        require(max >= 0) { "max must be positive, instead '$max' was given"}
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    fun min(min: Long): LongBuilderDsl {
        this.min = min
        return this
    }

    fun max(max: Long): LongBuilderDsl {
        this.max = max
        return this
    }
}

fun Long.Companion.of(transform: LongBuilderDsl.() -> LongBuilderDsl = { LongBuilderDsl() }): Long {
    return transform(LongBuilderDsl())()
}
