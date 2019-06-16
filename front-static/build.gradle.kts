
tasks {
//    getByName("build") {
//        dependsOn("slsBuild")
//    }
//    getByName("deploy") {
//        dependsOn("slsDeploy")
//    }

    val conf = project.configurations.create("serverlessArtifacts")
    val setArtifacts = create("setArtifacts") {
        inputs.files(fileTree("web"))
        artifacts.add(conf.name, fileTree("web").dir)
    }

    create("build").dependsOn(setArtifacts)
    create("deploy").dependsOn("build")

}

dependencies {
}
