import com.moowork.gradle.node.yarn.YarnTask

nodeSetup()

val projectMode: String by project

tasks {
    val buildWeb = "$buildDir/web"

    val constructArchive = task<YarnTask>("constructArchive") {
        dependsOn("yarn_install")

        inputs.files(fileTree("node_modules"))
        inputs.files(fileTree("src"))
        inputs.file("package.json")

        args = listOf(
            "run", "build",
            "--mode=$projectMode"
        )
    }

//    val constructArchive = task<Copy>("constructArchive") {
//        dependsOn("yarn_install")
//        from(projectDir)
//        exclude(
//            ".*",
//            "build",
//            "build.gradle.kts",
//            "package.json"
//        )
//        into(buildWeb)
//    }
//
    val conf = project.configurations.create("serverlessArtifacts")
    val setArtifacts = create("setArtifacts") {
        dependsOn(constructArchive)
        inputs.files(fileTree("src"))
        artifacts.add(conf.name, fileTree(buildWeb).dir)
    }

    get("build").dependsOn(setArtifacts)
    getByName<Delete>("clean") {
        delete.add("node_modules")
    }
    create("deploy").dependsOn("build")

}

dependencies {
}
