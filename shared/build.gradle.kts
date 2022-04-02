plugins {
    kotlin(BuildPlugins.multiplatformPlugin)
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.serializationPlugin) version BuildPlugins.Versions.kotlinSerializationPlugin
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(CommonLibraries.ktor)
                implementation(CommonLibraries.ktorLogging)
                implementation(CommonLibraries.ktorKotlinXSerialization)
                implementation(CommonLibraries.kotlinCoroutinesCore)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
//                implementation(TestLibraries.junit)
//                implementation(TestLibraries.ktor)
//                implementation(TestLibraries.mockk)
//                implementation(TestLibraries.kotlinCoroutinesTest)
//                implementation(TestLibraries.coreTesting)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libraries.kotlinCoroutinesAndroid)
                implementation(Libraries.ktorAndroid)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Libraries.ktorIOS)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }
}