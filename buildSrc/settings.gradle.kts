rootProject.buildFileName = "build.gradle.kts"
//enableFeaturePreview("GRADLE_METADATA")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
    resolutionStrategy {
        eachPlugin {
            println("DEP: ${requested.id.id} ${requested.version}")
            when (requested.id.id) {
                "kotlin-multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "kotlinx-serialization" -> "org.jetbrains.kotlin:kotlin-serialization:${requested.version}"
                "com.crowdproj.plugins.jar2npm" -> useModule("com.crowdproj.plugins:jar2npm-plugin:${requested.version}")
            }
        }
    }
}

fun configureGradleScriptKotlinOn(project: ProjectDescriptor) {
    project.buildFileName = "build.gradle.kts"
    project.children.forEach { configureGradleScriptKotlinOn(it) }
}

configureGradleScriptKotlinOn(rootProject)
