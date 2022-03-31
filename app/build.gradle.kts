plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.firebaseAppDistributionPlugin)
    id(BuildPlugins.kotlinKapt)
//    id(BuildPlugins.hiltAndroidPlugin)
    kotlin(BuildPlugins.serializationPlugin) version BuildPlugins.Versions.kotlinSerializationPlugin
}

android {
    compileSdk = AndroidSdk.compileSdk

    defaultConfig {
        applicationId = "io.radev.roman"
        minSdk = AndroidSdk.minSdk
        targetSdk = AndroidSdk.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("roman-app-key")
            storePassword = System.getenv("ROMAN_APP_KEY_PASS")
            keyAlias = System.getenv("ROMAN_APP_KEY_ALIAS")
            keyPassword = System.getenv("ROMAN_APP_KEY_PASS")
        }
    }

    buildTypes {
        release {
            firebaseAppDistribution {
                artifactType = "APK"
                appId = System.getenv("ROMAN_APP_FIREBASE_ID")
                testers = "radek.stasiak@gmail.com"
            }
            signingConfig = signingConfigs.findByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = BuildPlugins.Versions.kotlinCompilerExtensionVersion
    }


    buildFeatures {
        dataBinding = true
        compose = true

    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(Libraries.ktxCore)
    implementation(Libraries.ktxLifecycleRuntime)
    implementation(Libraries.appCompat)
    implementation(Libraries.androidMaterial)
    implementation(Libraries.constraintLayout)
//    implementation(Libraries.hiltAndroid)
//    kapt(Libraries.kaptHilt)
    implementation(Libraries.koin)
    implementation(Libraries.ktor)
    implementation(Libraries.ktorAndroid)
    implementation(Libraries.ktorLogging)
    implementation(Libraries.ktorKotlinXSerialization)
    implementation(Libraries.logbackClassics)
    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseAnalytics)

    //Compose
    implementation(Libraries.Compose.ui)
    implementation(Libraries.Compose.uiToolingPreview)
    implementation(Libraries.Compose.constraintLayout)
    implementation(Libraries.Compose.navigation)
    // Integration with activities
    implementation(Libraries.Compose.activity)
    // Compose Material Design
    implementation(Libraries.Compose.material)
    // Tooling support (Previews, etc.)
    implementation(Libraries.Compose.uiTooling)
    // Integration with ViewModels
    implementation(Libraries.Compose.viewModel)
    // When using a MDC theme
    implementation(Libraries.Compose.themeAdapter)
    implementation(Libraries.Compose.materialIcons)
    implementation(Libraries.Compose.animation)
    implementation(Libraries.Compose.koin)
    implementation(Libraries.Compose.runtimeLiveData)
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.ktor)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kotlinCoroutinesTest)
    testImplementation(TestLibraries.coreTesting)
    testImplementation(TestLibraries.turbine)

    androidTestImplementation(TestLibraries.extJunit)
    // UI Tests
    androidTestImplementation(TestLibraries.espressoCore)
    androidTestImplementation(TestLibraries.composeJunit)

}