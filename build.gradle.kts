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
    kotlin("jvm") version "1.7.21"
    kotlin("libs.publisher") version "0.0.60-dev-32"

    id("jacoco")
    id("com.diffplug.spotless") version "6.12.0"
    id("org.jetbrains.dokka") version "1.7.20"
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

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        configureEach {
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
        name.set(repository)
        description.set("Data generation dsl for Kotlin")

        githubRepo(organization, repository)

        developers {
            developer {
                id.set(organization)
                name.set("Stefan Koppier")
                email.set("stefan.koppier@outlook.com")
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