rootProject.name = "kotlin-builder-dsl"

dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            plugin("kotlin", "org.jetbrains.kotlin.jvm").version("1.7.21")
            plugin("spotless", "com.diffplug.spotless").version("6.12.0")
            plugin("dokka", "org.jetbrains.dokka").version("1.7.20")

            library("rgxgen","com.github.curious-odd-man", "rgxgen").version("1.4")
            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").version("1.7.22")
            library("mockito-kotlin", "org.mockito.kotlin", "mockito-kotlin").version("4.1.0")
        }
    }
}