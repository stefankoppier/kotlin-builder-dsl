package com.github.stefankoppier.builder.dsl

class FloatBuilderDsl: BuilderDsl<Float> {

    private var constant: Float? = null

    override fun invoke(): Float {
        return constant ?: Faker().nextFloat()
    }

    fun constant(value: Float): FloatBuilderDsl {
        constant = value
        return this
    }
}

fun Float.Companion.of(transform: FloatBuilderDsl.() -> FloatBuilderDsl = { FloatBuilderDsl() }): Float {
    return transform(FloatBuilderDsl())()
}