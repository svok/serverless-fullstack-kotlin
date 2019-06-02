import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node")
}

node {
    download = true
    workDir = file("${rootProject.buildDir}/node")
    npmWorkDir = file("${rootProject.buildDir}/node")
    yarnWorkDir = file("${rootProject.buildDir}/node")
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

    val copyStatic = task<Sync>("copyStatic") {
        from(project(":front-static").configurations.getByName("serverlessArtifacts").artifacts.files)
        into("$buildDir/web-static")
    }

    val copyAngular = task<Copy>("copyAngular") {
        dependsOn(copyStatic)
        from(project(":front-angular").configurations.getByName("serverlessArtifacts").artifacts.files)
        into("$buildDir/web-static/spa")
    }

    val jarDynamic = project(":front-dynamic").configurations.getByName("serverlessArtifacts").artifacts.files
    val copyDynamic = task<Copy>("copyDynamic") {
        from(jarDynamic)
        into("$buildDir/libs")
    }

    val copyAll = create("copyAll") {
        dependsOn(
            copyStatic,
            copyAngular,
            copyDynamic
        )
    }

    val slsArgs = arrayOf(
        "--no-confirm",
        "--stage", stage,
        "--domain", projectDomain,
        "--domain-cert", domainCertificate,
        "--jar", jarDynamic.joinToString(",")
    )

    val ngBuild = task<YarnTask>("ngBuild") {
        dependsOn("yarn_install", copyAll)

        inputs.files(fileTree("node_modules"))
        inputs.file("serverless.yml")
        inputs.file("package.json")

//        outputs.dir("dist")

        args = listOf("run", "pack", *slsArgs)
    }

    val ngDeploy = task<YarnTask>("ngDeploy") {
        dependsOn("yarn_install", copyAll)

        inputs.files(fileTree("node_modules"))
        inputs.file("serverless.yml")
        inputs.file("package.json")

        args = listOf("run", "deploy", *slsArgs)
    }

    clean.get().doLast {
        file("$projectDir/dist").deleteRecursively()
        file(buildDir).deleteRecursively()
        file("$projectDir/node_modules").deleteRecursively()
    }

    build.get().dependsOn(ngBuild)
    create("deploy") {
        dependsOn(ngDeploy)
        group = "build" 
    }

}

dependencies {
//    implementation(project(":proj-common"))
    implementation(project(":front-static", configuration = "serverlessArtifacts"))
    implementation(project(":front-angular", configuration = "serverlessArtifacts"))
    implementation(project(":front-dynamic", configuration = "serverlessArtifacts"))
}