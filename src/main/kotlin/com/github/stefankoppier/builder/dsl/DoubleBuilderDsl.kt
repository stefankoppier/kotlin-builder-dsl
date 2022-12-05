package com.github.stefankoppier.builder.dsl

class DoubleBuilderDsl: BuilderDsl<Double> {

    private var constant: Double? = null

    override fun invoke(): Double {
        return constant ?: Faker().nextDouble()
    }

    fun constant(value: Double): DoubleBuilderDsl {
        constant = value
        return this
    }
}

fun Double.Companion.of(transform: DoubleBuilderDsl.() -> DoubleBuilderDsl = { DoubleBuilderDsl() }): Double {
    return transform(DoubleBuilderDsl())()
}