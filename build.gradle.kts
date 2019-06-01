buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
}

plugins {
    val kotlin_version: String by project
    val node_plugin_version: String by project

    kotlin("multiplatform") version kotlin_version apply false
//    id("maven-publish")
    id("com.moowork.node") version node_plugin_version apply false
    id("io.spring.dependency-management") version "1.0.7.RELEASE" apply false
    id("com.github.johnrengelman.shadow") version "5.0.0" apply false
    id("com.crowdproj.plugins.jar2npm") version "1.0.1" apply false
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

