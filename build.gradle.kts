val repository = "kotlin-builder-dsl"
val organization = "stefankoppier"
val github = "github.com/$organization/$repository"

repositories {
    mavenCentral()
}

plugins {
    id("jacoco")
    id("maven-publish")
    id("signing")
    alias(libraries.plugins.kotlin)
    alias(libraries.plugins.spotless)
    alias(libraries.plugins.dokka)
}

group = "io.github.stefankoppier"
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
                remoteUrl.set(uri("https://$github/blob/master/src/main/kotlin").toURL())
                remoteLineSuffix.set("#L")
            }
        }
    }
}

java {
    withSourcesJar()
}

tasks.register<Jar>("javadocJar") {
    dependsOn("dokkaJavadoc")
    archiveClassifier.set("javadoc")
    from(tasks.named("dokkaJavadoc"))
}

publishing {
    publications {
        create<MavenPublication>(repository) {
            from(components["kotlin"])
            artifact(tasks.named("sourcesJar").get())
            artifact(tasks.named("javadocJar").get())
            artifactId = repository

            pom {
                name.set("$group:$repository")
                description.set("Data generation dsl for Kotlin")

                developers {
                    developer {
                        id.set(organization)
                        name.set("Stefan Koppier")
                        email.set("stefan.koppier@outlook.com")
                        url.set("https://github.com/StefanKoppier")
                    }
                }

                scm {
                    connection.set("scm:git:git@$github.git")
                    developerConnection.set("scm:git:git@$github.git")
                    url.set("https://$github/tree/main")
                }

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://$github/issues")
                }
            }
        }
    }

    repositories {
        mavenCentral {
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = properties["ussrhUsername"] as String
                password = properties["ussrhPassword"] as String
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications[repository])
}

spotless {
    kotlin {
        ktfmt().dropboxStyle().configure { options ->
            options.setMaxWidth(120)
        }
    }
}

