import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android.gradle)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.firebase.crashlytics)
    id("com.google.gms.google-services") // Plugin de Google Services en su lugar correcto
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.pelipunto.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pelipunto.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "TMDB_API_KEY", "\"${localProperties.getProperty("tmdb_api_key")}\"")
        }
        release {
            buildConfigField("String", "TMDB_API_KEY", "\"${localProperties.getProperty("tmdb_api_key")}\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Actualizado a Java 17
        targetCompatibility = JavaVersion.VERSION_17 // Actualizado a Java 17
    }
    kotlinOptions {
        jvmTarget = "17" // Actualizado a Java 17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core y Lifecycle de AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.ui.util)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Navegación
    implementation(libs.androidx.navigation.compose)

    // Hilt (Inyección de Dependencias)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Red (Networking)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // Imágenes
    implementation(libs.coil.compose)

    // Firebase (Usando la BoM)
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation(libs.firebase.crashlytics)
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Google Services y Coroutines
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.0")

    // Librerías de terceros (Accompanist y Media3)
    implementation("com.google.accompanist:accompanist-pager-indicators:0.34.0")
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

// ESTE BLOQUE ES LA SOLUCIÓN AL ERROR DE SERIALIZACIÓN
configurations.all {
    resolutionStrategy {
        force(libs.kotlinx.serialization.json)
    }
}