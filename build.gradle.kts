plugins {
    kotlin("jvm") version "1.7.10"
    id("info.solidsoft.pitest") version "1.7.4"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.4"
}

group = "com.github.caay2000"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<Wrapper> {
    gradleVersion = "7.5"
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    implementation("io.arrow-kt:arrow-core:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.4")
    implementation("org.openjdk.jmh:jmh-core:1.22")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation("org.openjdk.jmh:jmh-core:1.22")
    testImplementation("org.openjdk.jmh:jmh-generator-bytecode:1.22")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.22")
}

pitest {
    pitestVersion.set("1.9.3")
    junit5PluginVersion.set("1.0.0")
    targetClasses.add("com.github.caay2000.*")
    excludedMethods.addAll("toString")
    excludedTestClasses.add("**.*IntegrationTest")
    outputFormats.addAll("HTML")
    avoidCallsTo.add("kotlin.jvm.internal")
    mutators.addAll("STRONGER")
    detectInlinedCode.set(true)
    timestampedReports.set(false)
    threads.set(4)
    failWhenNoMutations.set(false)
}

benchmark {
    targets {
        register("main")
    }
}
