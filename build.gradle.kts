repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.7.21"

    id("maven-publish")
    id("jacoco")
}

group = "com.github.stefankoppier"
version = "0.0.1"

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("com.github.curious-odd-man:rgxgen:1.4")

    testImplementation(kotlin("test"))
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport  {
    reports {
        csv.required.set(true)
    }
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