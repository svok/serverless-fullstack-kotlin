import com.moowork.gradle.node.yarn.YarnTask

nodeSetup()

val projectMode: String by project

tasks {
    val buildWeb = "$buildDir/web"

    val createConfig = create("createConfig") {
        val awsUserPoolId: String by project
        val awsRegion: String by project
        val awsClientId: String by project
        val text = """
            window._config = {
                cognito: {
                    userPoolId: '$awsUserPoolId',
                    region: '$awsRegion',
                    clientId: '$awsClientId'
                },
            };
        """.trimIndent()
        val file = File("$projectDir/src/js/config.js")
        file
            .outputStream()
            .writer()
            .apply {
                write(text)
                close()
            }
    }

    val constructArchive = task<YarnTask>("constructArchive") {
        dependsOn("yarn_install")
        dependsOn(createConfig)

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
