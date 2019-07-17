val projectGroup: String by project
val projectVersion: String by project

group = projectGroup
version = projectVersion

plugins {
    kotlin("multiplatform")
    id("io.spring.dependency-management")
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

val kotlin_version: String by project

kotlin {
    sourceSets {
        jvm() {
            val main by compilations.getting {
                defaultSourceSet  { /* ... */ }
            }
        }
        js() {
            val main by compilations.getting {
                kotlinOptions {
                    metaInfo = true
                    sourceMap = true
                    moduleKind = "commonjs"
                    outputFile = "$buildDir/common.js"
                }
            }
        }

        @Suppress("UNUSED_PARAMETER")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        @Suppress("UNUSED_PARAMETER")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        @Suppress("UNUSED_PARAMETER")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_PARAMETER")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        @Suppress("UNUSED_PARAMETER")
        val jsMain by getting {

        }
        @Suppress("UNUSED_PARAMETER")
        val jsTest by getting {

        }
//        js().compilations["main"].defaultSourceSet  { /* ... */ }
//        js().compilations["test"].defaultSourceSet { /* ... */ }

//        mingwX64("mingw").compilations["main"].defaultSourceSet { /* ... */ }
//        mingwX64("mingw").compilations["test"].defaultSourceSet { /* ... */ }
    }

}
