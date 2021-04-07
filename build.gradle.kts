import java.text.SimpleDateFormat
import java.util.Date
import org.gradle.api.tasks.GradleBuild

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
        classpath("uk.gov.hmrc.gradle:spotless:1.0.0")
    }
}

/***********************************************************************************************************************
 * Project Gradle Config
 ***********************************************************************************************************************/

apply(plugin = "uk.gov.hmrc.spotless")

group = "uk.gov.hmrc"
description = "Multiplatform Tax Calculator library"
version = System.getenv("BITRISE_GIT_TAG") ?: ("SNAPSHOT-" + getDate())

plugins {
    `maven-publish`
    kotlin("multiplatform").version("1.4.31")
    jacoco
    java
    id("com.github.dawnwords.jacoco.badge").version("0.1.0")
    id("io.gitlab.arturbosch.detekt").version("1.6.0")
    id("com.chromaticnoise.multiplatform-swiftpackage").version("2.0.3")
}

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}


/***********************************************************************************************************************
 * Declarations
 ***********************************************************************************************************************/

val artifactId = "tax-kalculator"
val frameworkName = "TaxKalculator"

/***********************************************************************************************************************
 * Kotlin Configuration
 ***********************************************************************************************************************/

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
        languageSettings.useExperimentalAnnotation("kotlin.Experimental")
    }

    sourceSets {
        val klockVersion = "2.0.1"
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("com.soywiz.korlibs.klock:klock:$klockVersion")
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

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStandardStreams = true
        events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

/***********************************************************************************************************************
 * Swift Package Manager Configuration
 ***********************************************************************************************************************/

multiplatformSwiftPackage {
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("11") }
    }
    outputDirectory(File(projectDir, "build/xcframework"))
}

/***********************************************************************************************************************
 * GitHubPackages publishing
 ***********************************************************************************************************************/

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

/***********************************************************************************************************************
 * Other Task Configuration
 ***********************************************************************************************************************/

configurations {
    compileClasspath
}

jacocoBadgeGenSetting {
    jacocoReportPath = "$buildDir/reports/jacoco/testCommonUnitTestCoverage/testCommonUnitTestCoverage.xml"
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

tasks.jacocoTestCoverageVerification {
    group = project.name

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
    executionData.setFrom(files("${project.buildDir}/jacoco/jvmTest.exec"))
}

/***********************************************************************************************************************
 * Custom Functions
 **********************************************************************************************************************/

fun getDate(): String {
    val date = Date()
    val format = "yyyyMMddHHmm"
    return SimpleDateFormat(format).format(date).toString()
}

/***********************************************************************************************************************
 * Custom Tasks
 ***********************************************************************************************************************/

tasks.register<JacocoReport>("testCommonUnitTestCoverage") {
    group = project.name
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
    group = project.name

    tasks = listOf(
        "clean",
        "cleanAllTests",
        "build",
        "testCommonUnitTestCoverage",
        "generateJacocoBadge",
        "jacocoTestCoverageVerification")
}
