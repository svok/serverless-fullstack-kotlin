import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.yarn.YarnTask
import org.gradle.api.Project

import org.gradle.kotlin.dsl.*

/**
 * Configures the current project as a Kotlin project by adding the Kotlin `stdlib` as a dependency.
 */
fun Project.serverless(block: ServerlessSettings.() -> Unit = {}) {

    plugins.apply(com.moowork.gradle.node.NodePlugin::class.java)
    plugins.apply(org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJsPlugin::class.java)

    extensions.configure(NodeExtension::class) {
        download = true
        workDir = file("${rootProject.buildDir}/node")
        npmWorkDir = file("${rootProject.buildDir}/node")
        yarnWorkDir = file("${rootProject.buildDir}/node")
        nodeModulesDir = file("${project.projectDir}")
    }


    this.tasks {
        val projectDomain: String by project
        val projectStage: String by project
        val domainCertificate: String by project

        val slsArgs = kotlin.arrayOf(
            "--no-confirm",
            "--stage", projectStage,
            "--domain", projectDomain,
            "--domain-cert", domainCertificate
        )

        val slsBuild = task<YarnTask>("slsBuild") {
            dependsOn("yarn_install")

            val slsSettings = ServerlessSettings(inputs, outputs, args = arrayOf())
            slsSettings.block()
            val arguments = slsSettings.args

            args = listOf("run", "pack", *slsArgs, *arguments)
        }

        val slsDeploy = task<YarnTask>("slsDeploy") {
            dependsOn("yarn_install")

            val slsSettings = ServerlessSettings(inputs, outputs, args = arrayOf())
            slsSettings.block()
            val arguments = slsSettings.args

            args = listOf("run", "deploy", *slsArgs, *arguments)
        }

        getByName("clean").doLast {
            file(buildDir).deleteRecursively()
            file("$projectDir/node_modules").deleteRecursively()
        }

//        getByName("build").dependsOn(slsBuild)
//        create("deploy") {
//            dependsOn(slsDeploy)
//            group = "build"
//        }
    }
}
