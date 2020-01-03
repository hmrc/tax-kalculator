import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Properties
import org.gradle.api.tasks.GradleBuild

plugins {
    `maven-publish`
    kotlin("multiplatform").version("1.3.61")
    jacoco
    java
    id("com.github.dawnwords.jacoco.badge").version("0.1.0")
    id("io.gitlab.arturbosch.detekt").version("1.1.1")
    id("com.diffplug.gradle.spotless").version("3.27.0")
    id("com.jfrog.bintray").version("1.8.4")
}

jacocoBadgeGenSetting {
    jacocoReportPath = "$buildDir/reports/jacoco/testCommonUnitTestCoverage/testCommonUnitTestCoverage.xml"
}

tasks.jacocoTestCoverageVerification {
    group = "verification"

    violationRules {
        rule {
            limit {
                minimum = "0.95".toBigDecimal()
            }
        }
    }
    val excludes = listOf("**/*Test*.*")
    val coverageSourceDirs = listOf("src/commonMain/kotlin")
    sourceDirectories.setFrom(files(coverageSourceDirs))
    classDirectories.setFrom(fileTree("${project.buildDir}/classes/kotlin/jvm/").exclude(excludes))
    executionData(files("${project.buildDir}/jacoco/jvmTest.exec"))
}


repositories {
    mavenCentral()
    jcenter()
    jcenter {
        url = uri("https://hmrc.bintray.com/releases/")
    }
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

fun getDate(): String {
    val date = Date()
    val format = "yyyyMMddHHmm"
    return SimpleDateFormat(format).format(date).toString()
}

group = "uk.gov.hmrc"
description = "Multiplatform Tax Calculator library"
version = System.getenv("BITRISE_GIT_TAG") ?: ("SNAPSHOT-" + getDate())

val frameworkName = "TaxKalculator"

kotlin {

    jvm()
    ios()
    val ios32 = iosArm32("ios32")
    val ios64 = iosX64("ios64")
    val iosSimulator = iosArm64("iosSimulator")

    configure(listOf(ios32, ios64, iosSimulator)) {
        binaries.framework {
            baseName = frameworkName
            embedBitcode("disable")
        }
    }

    sourceSets {
        val klockVersion = "1.8.0"
        val commonMain by getting {
            dependencies {
                // Kotlin
                implementation(kotlin("stdlib-common"))
                implementation("com.soywiz.korlibs.klock:klock:$klockVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                // Kotlin
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                // Kotlin
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val iosArm32Main by sourceSets.creating
        val ios64Main by sourceSets.getting
        val iosSimulatorMain by sourceSets.getting

        val iosMain by getting {
            dependsOn(commonMain)
            iosArm32Main.dependsOn(this)
            ios64Main.dependsOn(this)
            iosSimulatorMain.dependsOn(this)

            dependencies {
                //Kotlin
                implementation(kotlin("stdlib"))
            }
        }
        val iosArm32Test by sourceSets.creating
        val ios64Test by sourceSets.getting
        val iosSimulatorTest by sourceSets.getting
        val iosTest by getting {
            dependsOn(commonTest)
            iosArm32Test.dependsOn(this)
            ios64Test.dependsOn(this)
            iosSimulatorTest.dependsOn(this)
        }
    }

    tasks.register("iosTest") {
        val device = project.findProperty("iosDevice")?.toString() ?: "iPhone 8"
        this.dependsOn(ios64.binaries.getTest("DEBUG").linkTaskName)
        group = JavaBasePlugin.VERIFICATION_GROUP
        description = "Runs tests for target ios on an iOS simulator"

        doLast {
            val binary = iosSimulator.binaries.getTest("DEBUG").outputFile
            exec {
                commandLine(listOf("xcrun", "simctl", "spawn", device, binary.absolutePath))
            }
        }
    }

    // Create a task building a fat framework.
    tasks.register<org.jetbrains.kotlin.gradle.tasks.FatFrameworkTask>("fatFramework") {
        group = "build"

        // The fat framework must have the same base name as the initial frameworks.
        baseName = frameworkName

        // The default destination directory is "<build directory>/fat-framework".
        destinationDir = File(buildDir, "xcode-frameworks")


        val ios32Framework = ios32.binaries.getFramework(frameworkName, "RELEASE")
        val ios64Framework = ios64.binaries.getFramework(frameworkName, "RELEASE")
        val iosSimulatorFramework = iosSimulator.binaries.getFramework(frameworkName, "RELEASE")

        this.dependsOn(ios32Framework.linkTask)
        this.dependsOn(ios64Framework.linkTask)
        this.dependsOn(iosSimulatorFramework.linkTask)
        this.dependsOn(tasks.findByName("build"))
        // Specify the frameworks to be merged.
        from(
            ios32Framework,
            ios64Framework,
            iosSimulatorFramework
        )

        doLast {
            File(destinationDir, "gradlew").apply {
                setExecutable(true)
            }
                .writeText("#!/bin/bash\nexport 'JAVA_HOME=${System.getProperty("java.home")}'\ncd '${rootProject.rootDir}'\n./gradlew \$@\n")
        }
    }

}

configurations {
    compileClasspath
}

jacoco {
    toolVersion = "0.8.4"
}

detekt {
    input = files("src/commonMain/kotlin")
    config = files("detekt-config.yml")
    reports {
        html {
            enabled = true
            destination = file("build/reports/detekt/index.html")
        }
    }
}

// Create gradle task
tasks.register<JacocoReport>("testCommonUnitTestCoverage") {
    group = "Reporting"
    description = "Generate Jacoco coverage reports on the common module build."

    this.dependsOn("allTests")
    val excludes = listOf("**/*Test*.*")
    val coverageSourceDirs = listOf("src/commonMain/kotlin")
    executionData(files("${project.buildDir}/jacoco/jvmTest.exec"))

    reports {
        xml.isEnabled = true
        html.isEnabled = true
        sourceDirectories.setFrom(files(coverageSourceDirs))
        classDirectories.setFrom(fileTree("${project.buildDir}/classes/kotlin/jvm/").exclude(excludes))
    }
}

tasks.register<GradleBuild>("cleanBuildTestCoverage") {
    group = "verification"

    tasks = listOf(
        "clean",
        "cleanAllTests",
        "build",
        "testCommonUnitTestCoverage",
        "generateJacocoBadge",
        "jacocoTestCoverageVerification"
    )
}

bintray {
    val credentials = Properties()
    rootProject.file("credentials.properties").inputStream().use { credentials.load(it) }

    user = credentials.getProperty("bintray.user")
    key = credentials.getProperty("bintray.apikey")
    setPublications("jvm")
    setPublications("metadata")

    publish = true

    pkg = PackageConfig()
    pkg.repo = "mobile-releases"
    pkg.name = project.name
    pkg.userOrg = "hmrc"
    pkg.desc = project.description
    pkg.setLicenses("Apache-2.0")
    pkg.version.name = project.version.toString()
    pkg.version.released = Date().toString()
}

fun getYear(): String {
    return Calendar.getInstance().get(Calendar.YEAR).toString()
}

val licenseString = """\
/*
 * Copyright ${getYear()} HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
"""

spotless {
    kotlin {
        target("**/*.kt")
        ktlint()
        licenseHeader(licenseString, "package ") // TODO Check this yellow line?
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xuse-experimental=kotlin.Experimental"
        )
    }
}