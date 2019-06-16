import com.moowork.gradle.node.yarn.YarnTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.task

/**
 * Configures the current project as a Kotlin project by adding the Kotlin `stdlib` as a dependency.
 fun Project.serverless(block: ServerlessSettings.() -> Unit = {}) {

    nodeSetup()

    tasks {
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

    }
}
 */
