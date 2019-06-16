import com.moowork.gradle.node.NodeExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.nodeSetup() {
    plugins.apply {
        apply(com.moowork.gradle.node.NodePlugin::class.java)
        apply(org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJsPlugin::class.java)
        apply(com.crowdproj.plugins.jar2npm.KotlinJar2NpmPlugin::class.java)
    }

    extensions.configure(NodeExtension::class) {
        download = true
        workDir = file("${rootProject.buildDir}/node")
        npmWorkDir = file("${rootProject.buildDir}/node")
        yarnWorkDir = file("${rootProject.buildDir}/node")
        nodeModulesDir = file("${project.projectDir}")
    }
}
