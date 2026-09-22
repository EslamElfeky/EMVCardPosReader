plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.app.emvcard.paxgateway"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        consumerProguardFiles("proguard-rules.pro")
    }

    flavorDimensions += "hardware"
    productFlavors {
        create("mock") {
            dimension = "hardware"
        }
        create("pax") {
            dimension = "hardware"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core:common"))
    implementation(libs.androidx.core.ktx)
    "paxImplementation"(fileTree(mapOf("dir" to "src/pax/libs", "include" to listOf("*.jar"))))
}
