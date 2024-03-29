plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("maven-publish")
    id("maven-android-sdk-deployer")
}

android {
    namespace = "com.qburst.signuptemp"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

publishing{
    publications{
        register<MavenPublication>("release"){
            afterEvaluate {
                groupId = "com.github.nikhil-qburst"
                artifactId = "signuptemp"
                version = "1.0.1"
                from(components["release"])
                pom {
                    // Add additional metadata about your library
                    name.set("Your Library Name")
                    description.set("Description of your library")
                    url.set("com.github.nikhil-qburst/signup")

                    // Add dependencies if your library has any
                     dependencies {
                         implementation("androidx.core:core-ktx:1.9.0")
                         implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
                         implementation("androidx.activity:activity-compose:1.8.2")
                         implementation(platform("androidx.compose:compose-bom:2023.03.00"))
                         implementation("androidx.compose.ui:ui")
                         implementation("androidx.compose.ui:ui-graphics")
                         implementation("androidx.compose.ui:ui-tooling-preview")
                         implementation("androidx.compose.material3:material3")

                         val lifecycleRuntime = "2.6.2"
                         implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntime")
                         implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleRuntime")

                         testImplementation("junit:junit:4.13.2")
                         androidTestImplementation("androidx.test.ext:junit:1.1.5")
                         androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
                         androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
                         androidTestImplementation("androidx.compose.ui:ui-test-junit4")
                         debugImplementation("androidx.compose.ui:ui-tooling")
                         debugImplementation("androidx.compose.ui:ui-test-manifest")
                     }
                }
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    val lifecycleRuntime = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntime")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleRuntime")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}