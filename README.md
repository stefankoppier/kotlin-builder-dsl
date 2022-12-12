![Coverage](.github/badges/jacoco.svg) 
[![[Maven]](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fio%2Fgithub%2Fstefankoppier%2Fkotlin-builder-dsl%2Fmaven-metadata.xml)](https://mvnrepository.com/artifact/io.github.stefankoppier/kotlin-builder-dsl)

# Kotlin Builder DSL
This library provides a domain-specific language (DSL) to build data for different data types.
The data types that are included are

| Data type        | Has `of` interface | Note                                                                                      |
|------------------|--------------------|-------------------------------------------------------------------------------------------|
| Byte             | &check;            |                                                                                           |
| Int              | &check;            |                                                                                           |
| Long             | &check;            |                                                                                           |
| Float            | &check;            |                                                                                           |
| Double           | &check;            |                                                                                           |
| Boolean          | &check;            |                                                                                           |
| String           | &check;            |                                                                                           |
| Uri              |                    |                                                                                           |
| BigDecimal       |                    |                                                                                           |
| LocalDateBuilder |                    |                                                                                           |
| OffsetDateTime   |                    |                                                                                           |
| List             |                    |                                                                                           |
| Enum             |                    | Is an abstract class which needs a concrete base class. [For example](#generating-enums). |

## Usage
First, add the dependency for e.g. Gradle:
```kotlin
dependencies {
    testImplementation("io.github.stefankoppier:kotlin-builder-dsl:0.0.1")
}
```
Then simply use the builders! For some examples, see [the examples](#examples)

### Nullability
By default, all builders generate non-nullable data types. If we want to generate a 
nullable data type, we can use the `NullableBuilder`. We can generate one by
```kotlin
NullableBuilder(IntBuilder())()
```
where the result is either a random `Int`or `null` each option with a probability 
of 50%.

## Examples

### Generating a random `Int`
We can generate a random `Int`
```kotlin
Int.of()
```

### Generating a random positive `Long`
We can generate a positive `Long`
```kotlin
Long.of { min(0) }
```

### Generating a constant `Long`
We can generate the constant `Long` with value `10`
```kotlin
Long.of { constant(10) }
```

### Generating a `String` that satisfies a regex
We can generate `String` instances that satsify arbitrary regexes. The following will 
generate a `String` that satisfies the pattern `[aAzZ]+`
```kotlin
String.of { format("[aAzZ]+") }
```

### Generating a `List` of random size
We can generate `List` of random size with `Int` elements whose value lies between `0` and `5`.
```kotlin
ListBuilder(IntBuilder().between(0, 5))()
```

### Generating enums
We can generate enum values as well. To do so, we first need to provide a concrete base class
and override the function `allValues`. For example, for `DayOfWeek` we can define
```kotlin
class DayOfWeekEnumBuilder(faker: Faker = Faker()) : EnumBuilder<DayOfWeek> {
    override fun allValues(): Array<DayOfWeek> {
        return DayOfWeek.values()
    }
}
```
which can then be used
```kotlin
DayOfWeekEnumBuilder().filter { it != DayOfWeek.MONDAY }()
```
where the result is a random `DayOfWeek` that is not `DayOfWeek.MONDAY`.

## Additional information
For more information, visit the [project documentation](https://stefankoppier.github.io/kotlin-builder-dsl/)