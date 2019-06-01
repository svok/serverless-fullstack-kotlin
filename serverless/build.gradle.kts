import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node")
}

node {
    download = true
    workDir = file("${project.buildDir}/node")
    npmWorkDir = file("${project.buildDir}/node")
    yarnWorkDir = file("${project.buildDir}/node")
    nodeModulesDir = file("${project.projectDir}")
}

tasks {

    val projectDomain: String by project
    val domainCertificate: String by project

    val stageVersionRegex = Regex("^(\\d+)\\.(\\d+)")
    val stage = stageVersionRegex
        .find(rootProject.version.toString())
        ?.groupValues
        ?.let { "v${it[1]}${it[2].padStart(2, '0')}" }
        ?: throw GradleException("Cannot parse version. Version must contain groups of numbers: 11.22.33")

    val slsArgs = arrayOf(
        "--stage", stage,
        "--domain", projectDomain,
        "--domain-cert", domainCertificate,
        "--jar", "some-jar"
    )


    val ngBuild = task<YarnTask>("ngBuild") {
        dependsOn("yarn_install")

        inputs.files(fileTree("node_modules"))
        inputs.file("serverless.yml")
        inputs.file("package.json")

//        outputs.dir("dist")

        args = listOf("run", "pack")
    }

}

dependencies {
//    implementation(project(":proj-common"))
}
