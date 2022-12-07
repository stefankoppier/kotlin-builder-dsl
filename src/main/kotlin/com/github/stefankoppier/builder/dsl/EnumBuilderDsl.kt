package com.github.stefankoppier.builder.dsl

abstract class EnumBuilderDsl<E : Enum<E>>(val faker: Faker = Faker()) : BuilderDsl<E> {

    private var filter: (E) -> Boolean = { true }

    protected abstract fun allValues(): Array<E>

    override fun invoke(): E {
        val options = allValues().filter(filter)
        return options[faker.int(0, options.size)]
    }

    fun filter(predicate: (E) -> Boolean): EnumBuilderDsl<E> {
        this.filter = predicate
        return this
    }
}
