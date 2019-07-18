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

    // Basic setup for API Gateway
    val deployBase = task<YarnTask>("deployBase") {
        val workDir = "$projectDir/sls-api-gw"
        dependsOn("yarn_install")

        inputs.files(fileTree("node_modules"))
        inputs.file("$workDir/serverless.yml")
        inputs.file("$workDir/package.json")
        setWorkingDir(File(workDir))

        args = listOf(
            "run", "deploy",
            "--no-confirm",
            "--stage", projectStage,
            "--domain", projectDomain,
            "--service", projectService
        )
    }

    // Tasks for Root static web-site
    val copyStatic = task<Sync>("copyStatic") {
        from(project(":front-static").configurations.getByName("serverlessArtifacts").artifacts.files)
        into("$buildDir/web-static")
    }
    val deployStatic = task<YarnTask>("deployStatic") {
        val workDir = "$projectDir/sls-front-static"
        dependsOn("yarn_install", copyStatic)

        inputs.files(fileTree("node_modules"))
        inputs.file("$workDir/serverless.yml")
        inputs.file("$workDir/package.json")
        setWorkingDir(File(workDir))

        args = listOf(
            "run", "deploy",
            "--no-confirm",
            "--stage", projectStage,
            "--domain", projectDomain,
            "--service", projectService
        )
    }
    val uploadStatic = task<Exec>("uploadStatic") {
        val publishDir = "$buildDir/web-static"
        dependsOn(deployStatic)
        inputs.files(fileTree(publishDir))
        commandLine = listOf(
            "aws",
            "s3",
            "cp",
            publishDir,
            "s3://$projectStage.$projectDomain-static/",
            "--recursive",
            "--include", "*"
        )
    }

    // Tasks for subpath SPA web-site
    val copySpa = task<Sync>("copySpa") {
        from(project(":front-angular").configurations.getByName("serverlessArtifacts").artifacts.files)
        into("$buildDir/web-spa")
    }
    val deploySpa = task<YarnTask>("deploySpa") {
        val workDir = "$projectDir/sls-front-angular"
        dependsOn("yarn_install", copySpa)

        inputs.files(fileTree("node_modules"))
        inputs.file("$workDir/serverless.yml")
        inputs.file("$workDir/package.json")
        setWorkingDir(File(workDir))

        args = listOf(
            "run", "deploy",
            "--no-confirm",
            "--stage", projectStage,
            "--domain", projectDomain,
            "--service", projectService
        )
    }
    val uploadSpa = task<Exec>("uploadSpa") {
        val publishDir = "$buildDir/web-spa"
        dependsOn(deploySpa)
        inputs.files(fileTree(publishDir))
        commandLine = listOf(
            "aws",
            "s3",
            "cp",
            publishDir,
            "s3://$projectStage.$projectDomain-spa/",
            "--recursive",
            "--include", "*"
        )
    }

    // Tasks for Lambda functions
    val jarDynamic = project(":front-dynamic").configurations.getByName("serverlessArtifacts").artifacts.files
    val copyDynamic = task<Sync>("copyDynamic") {
        from(jarDynamic)
        into("$buildDir/libs")
    }
    val deployDynamic = task<YarnTask>("deployDynamic") {
        val workDir = "$projectDir/sls-front-dynamic"
        dependsOn("yarn_install", copyDynamic)

        inputs.files(fileTree("node_modules"))
        inputs.file("$workDir/serverless.yml")
        inputs.file("$workDir/package.json")
        setWorkingDir(File(workDir))

        args = listOf(
            "run", "deploy",
            "--no-confirm",
            "--stage", projectStage,
            "--domain", projectDomain,
            "--service", projectService,
            "--jar", jarDynamic.joinToString(",")
        )
    }

    clean.get().doLast {
        file("$projectDir/dist").deleteRecursively()
        file(buildDir).deleteRecursively()
        file("$projectDir/node_modules").deleteRecursively()
    }

    build.get().dependsOn(
        ":front-static:build",
        ":front-angular:build",
        ":front-dynamic:build"
    )
    create("deploy") {
        group = "build"
        dependsOn(
            deployBase,
            uploadStatic,
            uploadSpa,
            deployDynamic
        )
        group = "build"
    }

}

dependencies {
    implementation(project(":front-static", configuration = "serverlessArtifacts"))
    implementation(project(":front-angular", configuration = "serverlessArtifacts"))
    implementation(project(":front-dynamic", configuration = "serverlessArtifacts"))
}
