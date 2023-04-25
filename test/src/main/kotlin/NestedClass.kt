import io.github.stefankoppier.builder.dsl.annotations.DataGenerator

@DataGenerator data class NestedClass(val inner: NestedClass?)
