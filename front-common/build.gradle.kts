//val projectGroup: String by project
//val projectVersion: String by project

//group = projectGroup
//version = projectVersion

plugins {
    kotlin("multiplatform")
//    id("io.spring.dependency-management")
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
val coroutine_version: String by project
val serialization_version: String by project

kotlin {
    jvm() {
        val main by compilations.getting {

        }
    }
    js() {
        val main by compilations.getting {
            kotlinOptions {
                metaInfo = true
                sourceMap = true
                moduleKind = "commonjs"
//                outputFile = jsOutputFile
            }
        }
    }
    // For ARM, should be changed to iosArm32 or iosArm64
    // For Linux, should be changed to e.g. linuxX64
    // For MacOS, should be changed to e.g. macosX64
    // For Windows, should be changed to e.g. mingwX64
    // linuxX64("linux")
    sourceSets {
        @Suppress("UNUSED_PARAMETER")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutine_version")
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
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serialization_version")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
            }
        }
        @Suppress("UNUSED_PARAMETER")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serialization_version")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutine_version")
            }
        }
        @Suppress("UNUSED_PARAMETER")
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
//        val linuxMain by getting {
//        }
//        val linuxTest by getting {
//        }
    }
}
