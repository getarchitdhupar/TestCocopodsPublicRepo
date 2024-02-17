import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

version = "1.1.0"
val iOSBinaryName = "shared"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

//    val xcf = XCFramework()
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "shared"
//            xcf.add(this)
//            isStatic = true
//        }
//    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "${iOSBinaryName}"
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

/**
 * This task prepares Release Artefact for iOS XCFramework
 * Updates Podspec, Package.swift, Carthage contents with version and checksum
 */
tasks.register("prepareReleaseOfiOSXCFramework") {
    description = "Publish iOS framework to the Cocoa Repo"

    // Create Release Framework for Xcode
    dependsOn("assembleSharedReleaseXCFramework", "packageDistribution")

//    doLast {
//
//        // Update Podspec Version
//        val poddir = File("$rootDir/shared/$iOSBinaryName.podspec")
//        val podtempFile = File("$rootDir/shared/$iOSBinaryName.podspec.new")
//
//        val podreader = poddir.bufferedReader()
//        val podwriter = podtempFile.bufferedWriter()
//        var podcurrentLine: String?
//
//        while (podreader.readLine().also { currLine -> podcurrentLine = currLine } != null) {
//            if (podcurrentLine?.trim()?.startsWith("spec.version") == true) {
//                podwriter.write("    spec.version       = \"${version}\"" + System.lineSeparator())
//            } else if (podcurrentLine?.trim()?.startsWith("spec.source") == true) {
//                podwriter.write("    spec.source       = { :http => \"https://github.com/rakeshchander/CachingLibrary-KMM/releases/download/${version}/${iOSBinaryName}.xcframework.zip\"}" + System.lineSeparator())
//            } else {
//                podwriter.write(podcurrentLine + System.lineSeparator())
//            }
//        }
//        podwriter.close()
//        podreader.close()
//        podtempFile.renameTo(poddir)
//
//        // Update Cartfile Version
////        val cartdir = File("$rootDir/Carthage/$iOSBinaryName.json")
////        val carttempFile = File("$rootDir/Carthage/$iOSBinaryName.json.new")
////
////        val cartreader = cartdir.bufferedReader()
////        val cartwriter = carttempFile.bufferedWriter()
////        var cartcurrentLine: String?
////
////        while (cartreader.readLine().also { currLine -> cartcurrentLine = currLine } != null) {
////            if (cartcurrentLine?.trim()?.startsWith("{") == true) {
////                cartwriter.write("{" + System.lineSeparator())
////                cartwriter.write("    \"${version}\":\"https://github.com/rakeshchander/CachingLibrary-KMM/releases/download/${version}/${iOSBinaryName}.xcframework.zip\"," + System.lineSeparator())
////            } else if (cartcurrentLine?.trim()?.startsWith("\"${version}\"") == true) {
////                continue
////            } else {
////                cartwriter.write(cartcurrentLine + System.lineSeparator())
////            }
////        }
////        cartwriter.close()
////        cartreader.close()
////        carttempFile.renameTo(cartdir)
////
////        // Update Package.swift Version
////
////        // Calculate Checksum
////        val checksumValue: String = org.apache.commons.io.output.ByteArrayOutputStream()
////            .use { outputStream ->
////                // Calculate checksum
////                project.exec {
////                    workingDir = File("$rootDir")
////                    commandLine(
////                        "swift",
////                        "package",
////                        "compute-checksum",
////                        "${iOSBinaryName}.xcframework.zip"
////                    )
////                    standardOutput = outputStream
////                }
////
////                outputStream.toString()
////            }
////
////
////        val spmdir = File("$rootDir/Package.swift")
////        val spmtempFile = File("$rootDir/Package.swift.new")
////
////        val spmreader = spmdir.bufferedReader()
////        val spmwriter = spmtempFile.bufferedWriter()
////        var spmcurrentLine: String?
////
////        while (spmreader.readLine().also { currLine -> spmcurrentLine = currLine } != null) {
////            if (spmcurrentLine?.trim()?.startsWith("url") == true) {
////                spmwriter.write("    url: \"<GIT_REPO_URL>/releases/download/${version}/${iOSBinaryName}.xcframework.zip\"," + System.lineSeparator())
////            } else if (spmcurrentLine?.trim()?.startsWith("checksum") == true) {
////                spmwriter.write("    checksum: \"${checksumValue.trim()}\"" + System.lineSeparator())
////            } else {
////                spmwriter.write(spmcurrentLine + System.lineSeparator())
////            }
////        }
////        spmwriter.close()
////        spmreader.close()
////        spmtempFile.renameTo(spmdir)
//    }
}

tasks.create<Zip>("packageDistribution") {
    // Delete existing XCFramework
    delete("$rootDir/shared.xcframework")

    // Replace XCFramework File at root from Build Directory
    copy {
        from("$buildDir/XCFrameworks/release")
        into("$rootDir")
    }

    // Delete existing ZIP, if any
    delete("$rootDir/${iOSBinaryName}.xcframework.zip")
    // ZIP File Name - as per Carthage Nomenclature
    archiveFileName.set("${iOSBinaryName}.xcframework.zip")
    // Destination for ZIP File
    destinationDirectory.set(layout.projectDirectory.dir("../"))
    // Source Directory for ZIP
    from(layout.projectDirectory.dir("$rootDir"))
}

android {
    namespace = "com.example.testcocoapodspublicrepo"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
dependencies {
    implementation("androidx.core:core-ktx:+")
}
