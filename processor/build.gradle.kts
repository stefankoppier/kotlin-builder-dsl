val repository = "kotlin-builder-dsl-processor"
val organization = "stefankoppier"
val github = "github.com/$organization/$repository"

plugins {
    id("maven-publish")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.spotless)
}

dependencies {
    implementation(project(":runtime"))
    implementation(platform(libs.kotlin.bom))
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.symbol.processing.api)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
}

publishing {
    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")

            credentials {
                username = properties["ussrhUsername"] as String
                password = properties["ussrhPassword"] as String
            }
        }
    }

    publications {
        create<MavenPublication>(repository) {
            from(components["kotlin"])
            artifactId = repository

            pom {
                name.set("$group:$repository")
                description.set("Data generation dsl for Kotlin")
                url.set("https://$github")

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
}
