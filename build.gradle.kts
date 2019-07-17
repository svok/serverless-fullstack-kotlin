buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
}

plugins {
//    val kotlin_version: String by project
    kotlin("multiplatform") apply false //version kotlin_version apply false
//    id("maven-publish")
//    id("com.moowork.node") apply false //version node_plugin_version apply false
//    id("io.spring.dependency-management") version "1.0.7.RELEASE" apply false
    id("com.github.johnrengelman.shadow") version "5.0.0" apply false
//    id("com.crowdproj.plugins.jar2npm") version "1.0.1" apply false
    id("net.saliman.properties") version "1.5.1"
}

val projectGroup: String by project
val projectVersion: String by project
group = projectGroup
version = projectVersion

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    group = projectGroup
    version = projectVersion

    apply {
        plugin("net.saliman.properties")
    }
}

tasks {
    create("clean") {
        file("$projectDir/dist").deleteRecursively()
        file(buildDir).deleteRecursively()
        file("$projectDir/node_modules").deleteRecursively()
    }

    create("deploy") {
        group = "build"
        dependsOn(":serverless-aws:deploy")
    }
}
