plugins {
    id("jacoco")
    id("maven-publish")
    id("signing")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spotless)
    alias(libs.plugins.dokka)
}

allprojects {
    group = "io.github.stefankoppier"
    version = "0.0.3"

    repositories {
        mavenCentral()
    }

    tasks.withType<JacocoReport>  {
        reports {
            csv.required.set(true)
        }
    }

    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            ktfmt().dropboxStyle().configure { options ->
                targetExclude("build/generated/**/*.*")
                options.setMaxWidth(120)
            }
        }
    }
}
