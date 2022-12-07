package com.github.stefankoppier.builder.dsl.primitives

import com.github.stefankoppier.builder.dsl.BuilderDsl
import com.github.stefankoppier.builder.dsl.Faker

class IntBuilderDsl(private val faker: Faker = Faker()) : BuilderDsl<Int> {

    private var constant: Int? = null

    private var min = Int.MIN_VALUE

    private var max = Int.MAX_VALUE

    override fun invoke(): Int {
        return constant ?: faker.int(min, max)
    }

    fun constant(value: Int): IntBuilderDsl {
        constant = value
        return this
    }

    fun between(min: Int, max: Int): IntBuilderDsl {
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    fun min(min: Int): IntBuilderDsl {
        require(min >= 0) { "min must be positive, instead '$min' was given" }

        this.min = min
        return this
    }

    fun max(max: Int): IntBuilderDsl {
        require(max >= 0) { "max must be positive, instead '$max' was given" }

        this.max = max
        return this
    }
}

fun Int.Companion.of(transform: IntBuilderDsl.() -> IntBuilderDsl = { IntBuilderDsl() }): Int {
    return transform(IntBuilderDsl())()
}
