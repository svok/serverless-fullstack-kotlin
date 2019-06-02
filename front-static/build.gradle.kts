serverless {
    inputs.files(fileTree("node_modules"))
    inputs.files(fileTree("web"))
    inputs.file("serverless.yml")
    inputs.file("package.json")
    args = arrayOf(
        "--ttt"
    )
//            outputs.dir("dist")

}

tasks {
    getByName("build") {
        dependsOn("slsBuild")
    }
    getByName("deploy") {
        dependsOn("slsDeploy")
    }
}

dependencies {
    //    implementation(project(":proj-common"))
//    implementation(project(":front-static", configuration = "serverlessArtifacts"))
//    implementation(project(":front-angular", configuration = "serverlessArtifacts"))
//    implementation(project(":front-dynamic", configuration = "serverlessArtifacts"))
}
