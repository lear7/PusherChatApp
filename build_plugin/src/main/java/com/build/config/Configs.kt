package com.build.config

object BuildVers {
    const val compile_sdk = 31
    const val min_sdk = 21
    const val target_sdk = 31
}

object Vers {
    const val app_compat = "1.3.0"
    const val cardview = "1.0.0"
    const val constraint_layout = "2.0.4"
    const val core_ktx = "1.7.0"
    const val coroutines = "1.4.2"
    const val firebase = "29.0.2"
    const val fragment = "1.2.5"
    const val hilt = "2.40.5"
    const val lifecycle = "2.3.0-rc01"
    const val material = "1.4.0"
    const val recyclerview = "1.2.0"
    const val retrofit = "2.4.0"
    const val pusher = "1.8.0"
    const val swipe_refreshlayout = "1.1.0"
}

object Configs {
    const val app_compat = "androidx.appcompat:appcompat:${Vers.app_compat}"
    const val core_ktx = "androidx.core:core-ktx:${Vers.core_ktx}"

    // material
    const val material = "com.google.android.material:material:${Vers.material}"

    // view
    const val cardview = "androidx.cardview:cardview:${Vers.cardview}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Vers.constraint_layout}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Vers.recyclerview}"
    const val swipe_refreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Vers.swipe_refreshlayout}"

    // pusher
    const val pusher = "com.pusher:pusher-java-client:${Vers.pusher}"
    const val pusher_notification = "com.pusher:push-notifications-android:${Vers.pusher}"

    // retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Vers.retrofit}"
    const val retrofit_moshi = "com.squareup.retrofit2:converter-moshi:${Vers.retrofit}"

    // Jetpack
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Vers.lifecycle}"
    const val lifedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Vers.lifecycle}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Vers.fragment}"

    // Coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Vers.coroutines}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Vers.coroutines}"

    // hilt
    const val hilt = "com.google.dagger:hilt-android:${Vers.hilt}"
    const val hilt_kapt = "com.google.dagger:hilt-android-compiler:${Vers.hilt}"

    // Import the BoM for the Firebase platform
    const val firebase = "com.google.firebase:firebase-bom:${Vers.firebase}"

    // Declare the dependencies for the Firebase Cloud Messaging and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    const val firebase_message = "com.google.firebase:firebase-messaging-ktx"
    const val firebase_analytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebase_iid = "com.google.firebase:firebase-iid"

}
