package com.github.stefankoppier.builder.dsl

import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.random.Random

/**
 * Generate random fake data for different data types.
 *
 * @param random The random number generator
 */
class Faker(private val random: Random = Random.Default) {

    /**
     * Generate a random [Int] using the provided random number generator.
     *
     * @param min The (inclusive) minimum value.
     * @param max The (exclusive) maximum value.
     *
     * @return A random [Int] `r` such that `min <= r < max`.
     */
    fun int(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int {
        return if (min == max) min else random.nextInt(min, max)
    }

    /**
     * Generate a random [Long] using the provided random number generator.
     *
     * @param min The (inclusive) minimum value.
     * @param max The (exclusive) maximum value.
     *
     * @return A random [Long] `r` such that `min <= r < max`.
     */
    fun long(min: Long = Long.MIN_VALUE, max: Long = Long.MAX_VALUE): Long {
        return if (min == max) min else random.nextLong(min, max)
    }

    /**
     * Generate a random [Boolean] using the provided random number generator.
     *
     * @return A random [Boolean] `r`.
     */
    fun boolean(): Boolean {
        return random.nextBoolean()
    }

    /**
     * Generate a random [Float] using the provided random number generator.
     *
     * @param min The (inclusive) minimum value.
     * @param max The (exclusive) maximum value.
     *
     * @return A random [Float] `r` such that `min <= r < max`.
     */
    fun float(min: Float = Float.MIN_VALUE, max: Float = Float.MAX_VALUE): Float {
        return if (min == max) min else min + random.nextFloat() * (max - min)
    }

    /**
     * Generate a random [Double] using the provided random number generator.
     *
     * @param min The (inclusive) minimum value.
     * @param max The (exclusive) maximum value.
     *
     * @return A random [Double] `r` such that `min <= r < max`.
     */
    fun double(min: Double = Double.MIN_VALUE, max: Double = Double.MAX_VALUE): Double {
        return if (min == max) min else min + random.nextDouble() * (max - min)
    }

    /**
     * Generate a random [Char] using the provided random number generator.
     *
     * @param pool The possible characters to generate from.
     *
     * @return A random [Char] `r` such that `r in pool`.
     */
    fun char(pool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ' '): Char {
        return pool[int(0, pool.size)]
    }

    /**
     * Generate a random [String] using the provided random number generator.
     *
     * @param min The (inclusive) minimum length.
     * @param max The (exclusive) maximum length.
     *
     * @return A random alphanumeric [String] `r` such that `min <= r.length < max`.
     */
    fun string(min: Int = 0, max: Int = 24): String {
        require(min >= 0) { "min must be positive, instead '$min' was given" }
        require(min <= max) { "min must be less than or equal to max, instead min was '$min' and max was '$max'" }

        return (0..int(min, max)).map { char() }.joinToString(separator = "")
    }

    /**
     * Generate a random enum [E] using the provided random number generator.
     *
     * @param predicate Filter options for which the predicate does not hold.
     *
     * @return A random enum <E> `r` such that `predicate(r) == false`.
     */
    inline fun <reified E : Enum<E>> enum(predicate: (E) -> Boolean = { true }): E {
        val options = enumValues<E>().filter(predicate)
        return options[int(0, options.size)]
    }

    /**
     * Generate a random [LocalDate] using the provided random number generator.
     *
     * @return A random [LocalDate] `r` such that `LocalDate.EPOCH <= r < LocalDate.of(2050, 12, 31)`.
     */
    fun localDate(): LocalDate {
        val min = LocalDate.EPOCH.toEpochDay()
        val max = LocalDate.of(2050, 12, 31).toEpochDay()
        return LocalDate.ofEpochDay(long(min, max))
    }

    /**
     * Generate a random [LocalTime] using the provided random number generator.
     *
     * @return A random [LocalTime] `r`.
     */
    fun localTime(): LocalTime {
        return LocalTime.of(int(0, 23), int(0, 59), int(0, 59))
    }

    /**
     * Generate a random UTC [OffsetDateTime] using the provided random number generator.
     *
     * @return A random [OffsetDateTime] `r` with date component `d` such that `LocalDate.EPOCH <= d <
     * LocalDate.of(2050, 12, 31)`.
     */
    fun offsetDateTime(): OffsetDateTime {
        return OffsetDateTime.of(localDate(), localTime(), ZoneOffset.UTC)
    }
}
