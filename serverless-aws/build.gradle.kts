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
    val uploadStatic = task<Exec>("uploadStatic") {
        dependsOn(copyStatic)
        commandLine = listOf(
            "aws",
            "s3",
            "cp",
            "$buildDir/web-static",
            "s3://$projectStage.$projectDomain-static/",
            "--recursive",
            "--include", "*"
        )
    }

    val copySpa = task<Sync>("copyAngular") {
        dependsOn(copyStatic)
        from(project(":front-angular").configurations.getByName("serverlessArtifacts").artifacts.files)
        into("$buildDir/web-spa")
    }
    val uploadSpa = task<Exec>("uploadSpa") {
        dependsOn(copySpa)
        commandLine = listOf(
            "aws",
            "s3",
            "cp",
            "$buildDir/web-spa",
            "s3://$projectStage.$projectDomain-spa/",
            "--recursive",
            "--include", "*"
        )
    }

    val jarDynamic = project(":front-dynamic").configurations.getByName("serverlessArtifacts").artifacts.files
    val copyDynamic = task<Sync>("copyDynamic") {
        from(jarDynamic)
        into("$buildDir/libs")
    }

    val copyAll = create("copyAll") {
        dependsOn(
            copyStatic,
            copySpa,
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
        dependsOn(":front-static:build")
        dependsOn(":front-angular:build")
        dependsOn(":front-dynamic:build")
        dependsOn(uploadStatic, uploadSpa)
        group = "build"
    }

}

dependencies {
    implementation(project(":front-static", configuration = "serverlessArtifacts"))
    implementation(project(":front-angular", configuration = "serverlessArtifacts"))
    implementation(project(":front-dynamic", configuration = "serverlessArtifacts"))
}
