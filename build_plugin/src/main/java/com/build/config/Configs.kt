package com.build.config

object Vers {
    const val app_compat = "1.3.0"
    const val cardview = "1.0.0"
    const val constraint_layout = "2.0.4"
    const val coil = "0.11.0"
    const val core_ktx = "1.7.0"
    const val coroutines = "1.4.3"
    const val firebase = "29.0.2"
    const val flowbinding_version = "1.2.0"
    const val fragment = "1.2.5"
    const val hilt = "2.40.5"
    const val kotlin = "1.6.10"
    const val kotlin_serialization = "1.3.2"
    const val lifecycle = "2.3.0-rc01"
    const val material = "1.4.0"
    const val hilt_jetpack = "1.0.0-alpha03"
    const val navigation_view = "2.3.5"
    const val recyclerview = "1.2.0"
    const val retrofit = "2.4.0"
    const val pusher = "1.8.0"
    const val swipe_refreshlayout = "1.1.0"
}

object Deps {
    const val app_compat = "androidx.appcompat:appcompat:${Vers.app_compat}"
    const val core_ktx = "androidx.core:core-ktx:${Vers.core_ktx}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Vers.fragment}"

    // material
    const val material = "com.google.android.material:material:${Vers.material}"

    // view
    const val cardview = "androidx.cardview:cardview:${Vers.cardview}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Vers.navigation_view}"
    const val navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Vers.navigation_view}"
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
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Vers.retrofit}"

    // Jetpack
    const val lifecycle = "androidx.lifecycle:lifecycle-common-java8:${Vers.lifecycle}"
    const val lifecycle_scope = "androidx.lifecycle:lifecycle-runtime-ktx:${Vers.lifecycle}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Vers.lifecycle}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Vers.lifecycle}"

    // flow Platform bindings
    const val flow_binding =
        "io.github.reactivecircus.flowbinding:flowbinding-android:${Vers.flowbinding_version}"
    const val flow_activity =
        "io.github.reactivecircus.flowbinding:flowbinding-activity:${Vers.flowbinding_version}"
    const val flow_appcompat =
        "io.github.reactivecircus.flowbinding:flowbinding-appcompat:${Vers.flowbinding_version}"
    const val flow_core =
        "io.github.reactivecircus.flowbinding:flowbinding-core:${Vers.flowbinding_version}"
    const val flow_drawlayout =
        "io.github.reactivecircus.flowbinding:flowbinding-drawerlayout:${Vers.flowbinding_version}"
    const val flow_navigation =
        "io.github.reactivecircus.flowbinding:flowbinding-navigation:${Vers.flowbinding_version}"
    const val flow_recyclerview =
        "io.github.reactivecircus.flowbinding:flowbinding-recyclerview:${Vers.flowbinding_version}"
    const val flow_swipefresh_layout =
        "io.github.reactivecircus.flowbinding:flowbinding-swiperefreshlayout:${Vers.flowbinding_version}"
    const val flow_viewpager2 =
        "io.github.reactivecircus.flowbinding:flowbinding-viewpager2:${Vers.flowbinding_version}"

    // Material Components bindings
    const val flow_material =
        "io.github.reactivecircus.flowbinding:flowbinding-material:${Vers.flowbinding_version}"

    // hilt
    const val hilt = "com.google.dagger:hilt-android:${Vers.hilt}"
    const val hilt_kapt = "com.google.dagger:hilt-android-compiler:${Vers.hilt}"

    const val hilt_jetpack = "androidx.hilt:hilt-lifecycle-viewmodel:${Vers.hilt_jetpack}"
    const val hilt_jetpack_kapt = "androidx.hilt:hilt-compiler:${Vers.hilt_jetpack}"
    
    // Coroutines
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Vers.kotlin}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Vers.coroutines}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Vers.coroutines}"
    const val kotlinx_serialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-core:${Vers.kotlin_serialization}"

    const val coil = "io.coil-kt:coil:${Vers.coil}"

    // Import the BoM for the Firebase platform
    const val firebase = "com.google.firebase:firebase-bom:${Vers.firebase}"

    // Declare the dependencies for the Firebase Cloud Messaging and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    const val firebase_message = "com.google.firebase:firebase-messaging-ktx"
    const val firebase_analytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebase_iid = "com.google.firebase:firebase-iid"

}

object Libs {
    const val lib_data = ":base_data"
    const val lib_domain = ":base_domain"
}