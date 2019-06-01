val projectGroup: String by project
val projectVersion: String by project

group = projectGroup
version = projectVersion

plugins {
    kotlin("jvm")
    id("io.spring.dependency-management")
    id("com.github.johnrengelman.shadow")
}

repositories {
    maven { setUrl("http://repo.maven.apache.org/maven2") }
}

// If requiring AWS JDK, uncomment the dependencyManagement to use the bill of materials
//   https://aws.amazon.com/blogs/developer/managing-dependencies-with-aws-sdk-for-java-bill-of-materials-module-bom/
//dependencyManagement {
//    imports {
//        mavenBom("com.amazonaws:aws-java-sdk-bom:1.11.206")
//    }
//}

tasks {
    val conf = project.configurations.create("serverlessArtifacts")
    val setArtifacts = create("setArtifacts") {
        dependsOn(shadowJar)
        artifacts.add(conf.name, shadowJar.get().archiveFile)
    }

    build.get().dependsOn(setArtifacts)
}


val kotlin_version: String by project

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

    implementation("com.amazonaws:aws-lambda-java-core:1.1.0")
    implementation("com.amazonaws:aws-lambda-java-log4j2:1.0.0")
    implementation("com.amazonaws:aws-lambda-java-events:2.0.1")

    implementation("com.fasterxml.jackson.core:jackson-core:2.8.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.8.5")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.8.5")
}
