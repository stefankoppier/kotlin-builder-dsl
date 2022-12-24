package io.github.stefankoppier.builder.dsl
/**
 * Abstract DSL for building [Enum] objects using the given [Faker].
 *
 * To use this class, extend it and implement the method [allValues]. For example:
 * ```kotlin
 * class DayOfWeekEnumBuilderDsl(faker: Faker = Faker())
 *      : EnumBuilderDsl<DayOfWeek>(faker) {
 *         override fun allValues(): Array<DayOfWeek> {
 *             return DayOfWeek.values()
 *         }
 *     }
 * ```
 *
 * @param E The enum type.
 */
abstract class EnumBuilder<E : Enum<E>, B : EnumBuilder<E, B>>(val faker: Faker = Faker()) : BuilderDsl<E> {

    private var constant: E? = null

    private var filter: (E) -> Boolean = { true }

    protected abstract fun allValues(): Array<E>

    /**
     * Generates the object according to the provided instructions.
     *
     * @return A new enum [E].
     */
    override operator fun invoke(): E {
        if (constant != null) {
            return constant!!
        }
        val options = allValues().filter(filter)
        return options[faker.int(0, options.size)]
    }

    /**
     * Instruct the builder to generate a constant.
     *
     * @param value The value to generate.
     *
     * @return The DSL itself.
     */
    fun constant(value: E): B {
        this.constant = value
        return this as B
    }

    /**
     * Instruct the builder to generate an enum that satisfies the [predicate].
     *
     * @param predicate Filter values for which the predicate does not hold.
     *
     * @return The DSL itself.
     */
    fun filter(predicate: (E) -> Boolean): B {
        this.filter = predicate
        return this as B
    }
}
