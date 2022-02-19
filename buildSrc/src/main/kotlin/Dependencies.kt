const val kotlinVersion = "1.5.31"

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "7.1.1"
        const val googleServices = "4.3.10"
        const val firebaseAppDistribution = "2.2.0"
        const val hiltGradle = "2.38.1"
        const val kotlinCompilerExtensionVersion = "1.0.5"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val firebaseAppDistribution = "com.google.firebase:firebase-appdistribution-gradle:${Versions.firebaseAppDistribution}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradle}"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val firebaseAppDistributionPlugin = "com.google.firebase.appdistribution"
    const val kotlinKapt = "kotlin-kapt"
    const val hiltAndroidPlugin = "dagger.hilt.android.plugin"

}

object AndroidSdk {
    const val compileSdk = 31
    const val minSdk = 23
    const val targetSdk = compileSdk
}

object Libraries {
    private object Versions {
        const val ktxCore = "1.6.0"
        const val ktxLifecycleRuntime = "2.4.0"
        const val appCompat = "1.3.1"
        const val androidMaterial = "1.4.0"
        const val constraintLayout = "2.0.4"
        const val firebaseBom = "28.4.2"
        const val hilt = BuildPlugins.Versions.hiltGradle
        const val ktor = "1.6.7"
    }

    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"
    const val ktxLifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ktxLifecycleRuntime}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics"
    const val kaptHilt = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val ktor = "io.ktor:ktor-client-core:${Versions.ktor}"

    object Compose {
        private object Versions {
            const val compose = "1.0.3"
            const val composeConstraintLayout = "1.0.0-beta02"
            const val composeNavigation = "2.4.1"
            const val composeActivity = "1.3.1"
            const val composeMaterial = "1.0.5"
            const val composeAnimation = "1.0.5"
            const val composeUiTooling = "1.0.5"
            const val composeViewModel = "1.0.0-alpha07"
            const val composeThemeAdapter = "1.0.5"
            const val composeMaterialIcons = "1.0.3"
        }

        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeUiTooling}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
        const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
        const val material = "androidx.compose.material:material:${Versions.composeMaterial}"
        const val animation = "androidx.compose.animation:animation:${Versions.composeAnimation}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
        const val themeAdapter = "com.google.android.material:compose-theme-adapter:${Versions.composeThemeAdapter}"
        const val materialIcons = "androidx.compose.material:material-icons-extended:${Versions.composeMaterialIcons}"
    }


}

object TestLibraries {
    private object Versions {
        const val junit = "4.+"
        const val extJunit = "1.1.3"
        const val espressoCore = "3.4.0"
        const val composeJunit = "1.0.5"
    }

    const val junit = "junit:junit:${Versions.junit}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val composeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.composeJunit}"
}