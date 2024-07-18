import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.text.SimpleDateFormat
import java.util.Date
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest
import uk.gov.hmrc.Dependencies

buildscript {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/hmrc/mobile-gradle-plugins")
            credentials {
                username = System.getenv("GITHUB_USER_NAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    dependencies {
        classpath("uk.gov.hmrc.gradle:spotless:1.1.1")
    }
}

apply(plugin = "uk.gov.hmrc.spotless")

group = "uk.gov.hmrc"
description = "Multiplatform Tax Calculator library"
version = System.getenv("BITRISE_GIT_TAG") ?: ("SNAPSHOT-" + getDate())

plugins {
    `maven-publish`
    kotlin("multiplatform").version("1.9.24")
    java
    id("io.gitlab.arturbosch.detekt").version("1.23.6")
    id("com.chromaticnoise.multiplatform-swiftpackage").version("2.0.3")
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

// Configure source sets
kotlin {
    jvm()
    val iosX64 = iosX64()
    val iosArm64 = iosArm64()
    val iosSimulatorArm64 = iosSimulatorArm64()

    val xcFramework = XCFramework(Config.frameworkName)

    targets {
        configure(listOf(iosX64, iosArm64, iosSimulatorArm64)) {
            binaries.framework {
                baseName = Config.frameworkName
                xcFramework.add(this)
                embedBitcode("disable")
            }
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.Experimental")
    }

    sourceSets {
        commonMain.dependencies {
            with(Dependencies.Common.Main) {
                implementation(kotlin(stdlib))
                implementation(klock)
                implementation(kermit)
            }
        }

        commonTest.dependencies {
            with(Dependencies.Common.Test) {
                implementation(kotlin(common))
                implementation(kotlin(annotations))
                implementation(junit)
                runtimeOnly(jupiter)
            }
        }

        jvmMain.dependencies {
            with(Dependencies.JVM.Main) {
                implementation(kotlin(stdlib))
            }
        }

        jvmTest.dependencies {
            with(Dependencies.JVM.Test) {
                implementation(kotlin(test))
                implementation(kotlin(junit))
                implementation(jupiter)
            }
        }

        iosMain.dependencies {
            with(Dependencies.IOS.Main) {
                implementation(kotlin(stdlib))
            }
        }
    }
}

// Configure Swift Package Manager
multiplatformSwiftPackage {
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }
    outputDirectory(File(projectDir, "build/XCFrameworks/release"))
}

// Configure GitHub Packages publishing
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "$group.$artifactId"
            artifactId = artifactId
            version = version
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/hmrc/tax-kalculator")
            credentials {
                username = System.getenv("GITHUB_USER_NAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

configurations {
    compileClasspath
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

koverReport {
    defaults {
        verify {
            rule("Coverage rate") {
                bound {
                    minValue = 95
                    aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
                }
            }
        }
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.named<Jar>("jvmJar") {
    archiveFileName.set("${Config.artifactId}-$version.jar")
}

tasks.getByName<KotlinNativeSimulatorTest>("iosX64Test") {
    deviceId = "iPhone 15"
}

fun getDate(): String {
    val date = Date()
    val format = "yyyyMMddHHmm"
    return SimpleDateFormat(format).format(date).toString()
}

object Config {
    const val artifactId = "tax-kalculator"
    const val frameworkName = "TaxKalculator"
}
