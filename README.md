![Coverage](.github/badges/jacoco.svg)

# Kotlin Builder DSL
This library provides a domain-specific language (DSL) to build data for different data types.
The data types that are included are

| Data type        | Has `of` interface | Note                                                                                        |
|------------------|--------------------|---------------------------------------------------------------------------------------------|
| Int              | &check;            |                                                                                             |
| Long             | &check;            |                                                                                             |
| Float            | &check;            |                                                                                             |
| Double           | &check;            |                                                                                             |
| Boolean          | &check;            |                                                                                             |
| String           | &check;            |                                                                                             |
| BigDecimal       |                    |                                                                                             |
| LocalDateBuilder |                    |                                                                                             |
| OffsetDateTime   |                    |                                                                                             |
| List             |                    |                                                                                             |
| Enum             |                    | Is an abstract class which needs a concrete base class. [For example](###Generating enums). |

## Usage
First, add the dependency for e.g. Gradle:
```kotlin
dependencies {
    testImplementation("com.github.stefankoppier:kotlin-builder-dsl:0.0.1")
}
```
Then simply use the builders! For some examples, see [the examples](##Examples) 

## Examples

### Generating a random `Int`
```kotlin
Int.of()
```

### Generating a random positive `Long`
```kotlin
Long.of { min(0) }
```

### Generating a `String` that satisfies a regex
```kotlin
String.of { format("[aAzZ]+") }
```

### Generating a `List` of random size with `Int` elements between 0 and 5
```kotlin
ListBuilder(IntBuilder.between(0, 5))()
```

### Generating enums

```kotlin
class DayOfWeekEnumBuilder(faker: Faker = Faker()) : EnumBuilder<DayOfWeek> {
    override fun allValues(): Array<DayOfWeek> {
        return DayOfWeek.values()
    }
}
```

```kotlin
DayOfWeekEnumBuilder().filter { it != DayOfWeek.MONDAY }()
```

## Additional information
For more information, visit the [project documentation](https://stefankoppier.github.io/kotlin-builder-dsl/)