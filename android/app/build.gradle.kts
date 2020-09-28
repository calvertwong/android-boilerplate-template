import java.util.Properties
import java.io.FileInputStream

// Create a variable called keystorePropertiesFile, and initialize it to your
// keystore.properties file, in the rootProject folder.
private val keystorePropertiesFile: File = rootProject.file("keystore.properties")
var keystoreProperties: Properties? = null

if (keystorePropertiesFile.exists()) {
    // Initialize a new Properties() object called keystoreProperties.
    keystoreProperties = Properties()

    // Load your keystore.properties file into the keystoreProperties object.
    keystoreProperties?.load(FileInputStream(keystorePropertiesFile))
}

android {
    keystoreProperties?.let {
        signingConfigs {
            register("release") {
                keyAlias = keystoreProperties?.get("keyAlias") as String
                keyPassword = keystoreProperties?.get("keyPassword") as String
                storeFile = file(keystoreProperties?.get("storeFile") as String)
                storePassword = keystoreProperties?.get("storePassword") as String
            }
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                isShrinkResources = true
                signingConfig = signingConfigs.getByName("release")
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
    }
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.CoreKtx.coreKtx)
    implementation(Deps.AppCompat.appCompat)
    implementation(Deps.LegacySupport.legacySupport)

    implementation(Deps.Material.material)

    implementation(Deps.ConstraintLayout.constraintLayout)

    implementation(Deps.Lifecycle.lifecycleExtension)

    implementation(Deps.JetpackNavigation.navigationFragmentKtx)
    implementation(Deps.JetpackNavigation.navigationUiKtx)

    debugImplementation(Deps.DebugDrawer.debugDrawer)
    implementation(Deps.DebugDrawer.debugDrawerBase)
    implementation(Deps.DebugDrawer.debugDrawerActions)
    implementation(Deps.DebugDrawer.debugDrawerCommons)
    implementation(Deps.DebugDrawer.debugDrawerLogs)
    releaseImplementation(Deps.DebugDrawer.debugDrawerNoOp)
    implementation(Deps.DebugDrawer.debugDrawerOkhttp)
    implementation(Deps.DebugDrawer.debugDrawerOkhttp3)
    implementation(Deps.DebugDrawer.debugDrawerTimber)
    
    debugImplementation(Deps.Chuck.chuckLibrary)
    releaseImplementation(Deps.Chuck.chuckLibraryNoOp)

    implementation(Deps.Lynx.lynx)

    implementation(Deps.Koin.koinAndroid)

    implementation(Deps.OkHttp.okHttp)

    // Tests
    testImplementation(Deps.Junit.junit)
    androidTestImplementation(Deps.ExtJunit.extJunit)
    androidTestImplementation(Deps.Espresso.espressoCore)

    implementation(project(":navigation"))
    implementation(project(":core"))
}