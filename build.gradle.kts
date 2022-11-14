import java.text.SimpleDateFormat
import java.util.Date

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
    kotlin("multiplatform").version("1.7.20")
    java
    id("io.gitlab.arturbosch.detekt").version("1.6.0")
    id("com.chromaticnoise.multiplatform-swiftpackage").version("2.0.3")
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}


val artifactId = "tax-kalculator"
val frameworkName = "TaxKalculator"

// Configure source sets
kotlin {

    jvm()
    val iosX64 = iosX64("ios")
    val iosArm32 = iosArm32()
    val iosArm64 = iosArm64() //Simulator

    targets {
        configure(listOf(iosX64, iosArm32, iosArm64)) {
            binaries.framework {
                baseName = frameworkName
                embedBitcode("disable")
            }
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.Experimental")
    }

    sourceSets {
        val klockVersion = "2.0.7"
        val kermitVersion = "1.2.2"
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("com.soywiz.korlibs.klock:klock:$klockVersion")
                implementation("co.touchlab:kermit:$kermitVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter-params:5.7.1")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }

        val iosTest by getting {}

        val iosArm32Main by sourceSets.getting
        val iosArm64Main by sourceSets.getting

        configure(listOf(iosArm32Main, iosArm64Main)) {
            dependsOn(iosMain)
        }

        val iosArm32Test by sourceSets.getting
        val iosArm64Test by sourceSets.getting
        configure(listOf(iosArm32Test, iosArm64Test)) {
            dependsOn(iosTest)
        }
    }
}

// Configure Swift Package Manager
multiplatformSwiftPackage {
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("11") }
    }
    outputDirectory(File(projectDir, "build/xcframework"))
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

tasks.koverVerify {
    rule {
        name = "Coverage rate"
        bound {
            minValue = 95
            valueType = kotlinx.kover.api.VerificationValueType.COVERED_LINES_PERCENTAGE
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
    archiveFileName.set("$artifactId-$version.jar")
}

fun getDate(): String {
    val date = Date()
    val format = "yyyyMMddHHmm"
    return SimpleDateFormat(format).format(date).toString()
}
