import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("kotlin-platform-js")
    id("com.moowork.node")
    id("com.crowdproj.plugins.jar2npm")
}

nodeSetup()

tasks {

    withType<Jar> {
        dependsOn("ngBuild")
        archiveBaseName.set(project.name)
    }

    val ngBuild = task<YarnTask>("ngBuild") {
        dependsOn("yarn_install")

        inputs.files(fileTree("node_modules"))
        inputs.files(fileTree("src"))
        inputs.file("angular.json")
        inputs.file("package.json")
        inputs.file("build.gradle.kts")

        outputs.dir("dist")

        args = listOf("run", "build", "--base-href", "/spa/")
    }

    val webdriverUpdate = register("webdriverUpdate", YarnTask::class) {
        args = listOf("run", "update-driver")
    }

    task<YarnTask>("ngTest") {
        dependsOn("yarn_install")
        dependsOn("webdriverUpdate")
        args = listOf("run", "testPhantom")
    }

    task<YarnTask>("serve") {
        args = listOf("run", "start")
    }

    getByName("clean").doLast {
        file("$projectDir/dist").deleteRecursively()
        file(buildDir).deleteRecursively()
        file("$projectDir/node_modules").deleteRecursively()
    }

    compileKotlin2Js {
        kotlinOptions {
            metaInfo = true
            outputFile = "${project.buildDir.path}/js/index.js"
            sourceMap = true
            sourceMapEmbedSources = "always"
            moduleKind = "commonjs"
            main = "call"
        }
    }

    compileTestKotlin2Js {
        kotlinOptions {
            metaInfo = true
            outputFile = "${project.buildDir.path}/js-tests/${project.name}-tests.js"
            sourceMap = true
            moduleKind = "commonjs"
            main = "call"
        }
    }

    ngBuild.dependsOn(jar2npm)

    val conf = project.configurations.create("serverlessArtifacts")
    val setArtifacts = create("setArtifacts") {
        dependsOn(ngBuild)
        artifacts.add(conf.name, fileTree("dist/angularfront").dir)
    }

    build.get().dependsOn(setArtifacts)

}

dependencies {
//    implementation(project(":proj-common"))
}
