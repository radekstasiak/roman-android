const val kotlinVersion = "1.5.31"

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "7.1.1"
        const val googleServices = "4.3.10"
        const val firebaseAppDistribution = "2.2.0"
        const val hiltGradle = "2.38.1"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val firebaseAppDistribution = "com.google.firebase:firebase-appdistribution-gradle:${Versions.firebaseAppDistribution}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradle}"
}

object AndroidSdk {

}

object Libraries {

}

object TestLibraries {

}