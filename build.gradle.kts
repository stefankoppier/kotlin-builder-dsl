repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.7.21"

    id("maven-publish")
}

group = "com.github.stefankoppier"
version = "0.0.1"

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("com.github.curious-odd-man:rgxgen:1.4")
}

java {
    withSourcesJar()
}

publishing {
    publications {
        register("mavenKotlin", MavenPublication::class) {
            from(components["kotlin"])
            artifact(tasks.named("sourcesJar").get())
            artifactId  = "kotlin-builder-dsl"
        }
    }
}