dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.CoreKtx.coreKtx)

    implementation(Deps.Koin.koinAndroid)
}