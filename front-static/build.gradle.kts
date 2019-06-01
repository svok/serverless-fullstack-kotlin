import com.moowork.gradle.node.yarn.YarnTask

plugins {
    id("kotlin-platform-js")
//    id("com.moowork.node")
//    id("com.crowdproj.plugins.jar2npm")
}

//node {
//    download = true
//    workDir = file("${project.buildDir}/node")
//    npmWorkDir = file("${project.buildDir}/node")
//    yarnWorkDir = file("${project.buildDir}/node")
//    nodeModulesDir = file("${project.projectDir}")
//}

tasks {

    val conf = project.configurations.create("serverlessArtifacts")
    val setArtifacts = create("setArtifacts") {
        artifacts.add(conf.name, fileTree("dist/front").dir)
    }

    build.get().dependsOn(setArtifacts)
}

dependencies {
//    implementation(project(":proj-common"))
}
