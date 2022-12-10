import org.jetbrains.kotlinx.publisher.apache2
import org.jetbrains.kotlinx.publisher.githubRepo
import java.net.URL

val repository = "kotlin-builder-dsl"
val organization = "stefankoppier"
val github = "https://github.com/$organization/$repository"

repositories {
    mavenCentral()
}

plugins {
    id("jacoco")
    alias(libraries.plugins.kotlin)
    alias(libraries.plugins.kotlin.libs.publisher)
    alias(libraries.plugins.spotless)
    alias(libraries.plugins.dokka)
}

group = "com.github.stefankoppier"
version = "0.0.1"

dependencies {
    implementation(libraries.rgxgen.get())

    testImplementation(libraries.kotlin.test.get())
    testImplementation(libraries.mockito.kotlin.get())
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

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        configureEach {
            reportUndocumented.set(false)

            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("$github/blob/master/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}

kotlinPublications {
    defaultGroup.set("$group")

    publication {
        publicationName.set(name)
    }

    pom {
        name.set("$group:repository")
        description.set("Data generation dsl for Kotlin")

        githubRepo(organization, repository)

        developers {
            developer {
                id.set(organization)
                name.set("Stefan Koppier")
                email.set("stefan.koppier@outlook.com")
                url.set("https://github.com/StefanKoppier")
            }
        }

        licenses {
            apache2()
        }

        issueManagement {
            system.set("GitHub")
            url.set("{$github/issues")
        }
    }
}

spotless {
    kotlin {
        ktfmt().dropboxStyle().configure { options ->
            options.setMaxWidth(120)
        }
    }
}