import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node")
}

nodeSetup()

tasks {

    val projectDomain: String by project
    val projectStage: String by project
    val projectService: String by project
    val domainCertificate: String by project

    val copyStatic = task<Sync>("copyStatic") {
        from(project(":front-static").configurations.getByName("serverlessArtifacts").artifacts.files)
        into("$buildDir/web-static")
    }

    val copyAngular = task<Sync>("copyAngular") {
        dependsOn(copyStatic)
        from(project(":front-angular").configurations.getByName("serverlessArtifacts").artifacts.files)
        into("$buildDir/web-spa")
    }

    val jarDynamic = project(":front-dynamic").configurations.getByName("serverlessArtifacts").artifacts.files
    val copyDynamic = task<Sync>("copyDynamic") {
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
        "--stage", projectStage,
        "--domain", projectDomain,
        "--domain-cert", domainCertificate,
        "--jar", jarDynamic.joinToString(","),
        "--service", projectService
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

        setEnvironment(mapOf("SLS_DEBUG" to "*"))

        args = listOf("run", "deploy", *slsArgs)
    }

    clean.get().doLast {
        file("$projectDir/dist").deleteRecursively()
        file(buildDir).deleteRecursively()
        file("$projectDir/node_modules").deleteRecursively()
    }

    build.get().dependsOn(
        ngBuild,
        ":front-static:build",
        ":front-angular:build",
        ":front-dynamic:build"
    )
    create("deploy") {
        dependsOn(ngDeploy)
        dependsOn(":font-static:build")
        dependsOn(":font-angular:build")
        dependsOn(":font-dynamic:build")
        group = "build"
    }

}

dependencies {
    implementation(project(":front-static", configuration = "serverlessArtifacts"))
    implementation(project(":front-angular", configuration = "serverlessArtifacts"))
    implementation(project(":front-dynamic", configuration = "serverlessArtifacts"))
}
