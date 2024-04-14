plugins {
    id(Build.androidApplicationLib) version Build.androidApplicationVersion apply false
    id(Kotlin.kotlinLibrary) version Kotlin.version apply false
    id(Build.androidLibrary) version Build.androidApplicationVersion apply false
    id(Kotlin.kotlinVMLib) version Kotlin.version apply false
    id(DaggerHilt.hiltAndroidSrc) version DaggerHilt.version apply false


}