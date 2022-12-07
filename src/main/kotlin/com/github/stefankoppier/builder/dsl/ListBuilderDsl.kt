package com.github.stefankoppier.builder.dsl

class ListBuilderDsl<E, B : BuilderDsl<E>>(private val factory: B, private val faker: Faker = Faker()) :
    BuilderDsl<List<E>> {

    private var min: Int = 0

    private var max: Int = 10

    private var previous: (B.(E) -> B)? = null

    override fun invoke(): List<E> {
        val size = 0 until faker.int(min, max)

        if (previous != null) {
            val first = factory()
            val previous = previous!!
            return (size - 1).fold(mutableListOf(first)) { acc, _ -> acc.with(factory.previous(acc.last())()) }
        }
        return size.map { factory() }
    }

    fun between(min: Int, max: Int): ListBuilderDsl<E, B> {
        require(min >= 0) { "min must be positive, instead '$min' was given" }
        require(max >= 0) { "max must be positive, instead '$max' was given" }
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return min(min).max(max)
    }

    fun min(min: Int): ListBuilderDsl<E, B> {
        this.min = min
        return this
    }

    fun max(max: Int): ListBuilderDsl<E, B> {
        this.max = max
        return this
    }

    fun previous(transform: B.(E) -> B): ListBuilderDsl<E, B> {
        this.previous = transform
        return this
    }
}

private fun <E> MutableList<E>.with(element: E): MutableList<E> {
    this.add(element)
    return this
}
