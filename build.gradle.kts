
plugins {
    id("java")
    `kotlin-dsl`
    application
    id("nu.studer.jooq") version "8.0" apply false
}

group = "ru.girmank.vk"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

subprojects {

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("nu.studer.jooq")
    }

    dependencies {
        // https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-servlet
        implementation("org.eclipse.jetty:jetty-servlet:11.0.12")
        // https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-proxy
        implementation("org.eclipse.jetty:jetty-proxy:11.0.12")

        // https://mvnrepository.com/artifact/commons-io/commons-io
        implementation("commons-io:commons-io:2.11.0")

        implementation("org.jooq:jooq:3.17.4")
        implementation("org.jooq:jooq-codegen:3.17.4")
        implementation("org.jooq:jooq-meta:3.17.4")

        implementation("com.intellij:annotations:12.0")

        implementation("org.flywaydb:flyway-core:9.7.0")
        implementation("org.postgresql:postgresql:42.5.0")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}
