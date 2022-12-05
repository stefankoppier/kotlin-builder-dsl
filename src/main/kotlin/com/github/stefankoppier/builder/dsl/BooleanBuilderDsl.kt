package com.github.stefankoppier.builder.dsl

class BooleanBuilderDsl(private val faker: Faker = Faker())
    : BuilderDsl<Boolean> {

    private var constant: Boolean? = null

    override fun invoke(): Boolean {
        return constant ?: faker.nextBoolean()
    }

    fun constant(value: Boolean): BooleanBuilderDsl {
        constant = value
        return this
    }
}

fun Boolean.Companion.of(transform: BooleanBuilderDsl.() -> BooleanBuilderDsl = { BooleanBuilderDsl() }): Boolean {
    return transform(BooleanBuilderDsl())()
}