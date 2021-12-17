package com.build.config

object BuildVersion {
    const val compileSdkVersion = 31
    const val buildToolsVersion = "29.0.2"
    const val minSdkVersion = 21
    const val targetSdkVersion = 31
}

object Versions {
    const val app_compat = "1.3.0"
    const val cardview = "1.0.0"
    const val constraint_layout = "2.0.4"
    const val core_ktx = "1.7.0"
    const val material = "1.4.0"
    const val recyclerview = "1.2.0"
    const val swipe_refreshlayout = "1.1.0"
}

object BuildConfig {
    const val app_compat = "androidx.appcompat:appcompat:${Versions.app_compat}"

    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"

    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"

    const val material = "com.google.android.material:material:${Versions.material}"

    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

    const val swipe_refreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refreshlayout}"

}
