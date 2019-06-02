plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

dependencies {
        implementation(gradleApi())
        implementation(kotlin("stdlib-jdk8"))
        implementation("com.moowork.gradle:gradle-node-plugin:1.3.1")
        implementation(kotlin("gradle-plugin"))

//        testImplementation(kotlin("test"))
//        testImplementation(gradleTestKit())
//        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
//        testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
//        testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
//        testImplementation("org.assertj:assertj-core:3.12.2")
}
