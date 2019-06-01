tasks {

    val conf = project.configurations.create("serverlessArtifacts")
    val setArtifacts = create("setArtifacts") {
        artifacts.add(conf.name, fileTree("dist/front").dir)
    }

    create("build").dependsOn(setArtifacts)
}
