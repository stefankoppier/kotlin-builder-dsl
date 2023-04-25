plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
}

dependencies {
    ksp(project(":processor"))

    implementation(project(":runtime"))

    testImplementation(libs.kotlin.test)
    testImplementation(libs.assertj.core)
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

tasks.test {
    useJUnitPlatform()
}