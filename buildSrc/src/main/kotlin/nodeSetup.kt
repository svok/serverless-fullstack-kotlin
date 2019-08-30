import com.moowork.gradle.node.NodeExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.nodeSetup() {
    val yarn_version = property("yarn_version") as String

    plugins.apply {
        apply(com.moowork.gradle.node.NodePlugin::class.java)
        apply(org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJsPlugin::class.java)
        apply(com.crowdproj.plugins.jar2npm.KotlinJar2NpmPlugin::class.java)
    }

    extensions.configure(NodeExtension::class) {
        yarnVersion = yarn_version
        download = true
        workDir = file("${rootProject.buildDir}/node")
        npmWorkDir = file("${rootProject.buildDir}/node")
        yarnWorkDir = file("${rootProject.buildDir}/node")
        nodeModulesDir = file("${project.projectDir}")
    }
}
