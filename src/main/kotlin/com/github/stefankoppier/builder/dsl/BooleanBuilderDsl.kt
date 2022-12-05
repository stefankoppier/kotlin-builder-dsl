package com.github.stefankoppier.builder.dsl

class BooleanBuilderDsl: BuilderDsl<Boolean> {

    private var constant: Boolean? = null

    override fun invoke(): Boolean {
        return constant ?: Faker().nextBoolean()
    }

    fun constant(value: Boolean): BooleanBuilderDsl {
        constant = value
        return this
    }
}

fun Boolean.Companion.of(transform: BooleanBuilderDsl.() -> BooleanBuilderDsl = { BooleanBuilderDsl() }): Boolean {
    return transform(BooleanBuilderDsl())()
}