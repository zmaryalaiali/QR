plugins {
    id("com.android.application")
}
val codeScannerVersion by extra("2.1.0")
val codeScannerVersion1 by extra(codeScannerVersion)

android {
    namespace = "com.zmaryalaiali.qr"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zmaryalaiali.qr"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation("com.google.zxing:core:3.4.1")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
//
//    // QR
//    implementation("com.journeyapps:zxing-android-embedded:4.2.0")

    // scanner
//    implementation("com.github.budiyev.android:code-scanner:2.1.0")

//    implementation("com.budiyev.android:code-scanner:$codeScannerVersion1")

//    implementation("com.github.AndroidMad:QRGen:${rootProject.extra["qrgenVersion"]}")




}