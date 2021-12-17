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
    const val material = "1.4.0"
    const val recyclerview = "1.2.0"
    const val swipe_refreshlayout = "1.1.0"
}

object Configs {
    const val app_compat = "androidx.appcompat:appcompat:${Vers.app_compat}"

    const val cardview = "androidx.cardview:cardview:${Vers.cardview}"

    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Vers.constraint_layout}"

    const val core_ktx = "androidx.core:core-ktx:${Vers.core_ktx}"

    const val material = "com.google.android.material:material:${Vers.material}"

    const val recyclerview = "androidx.recyclerview:recyclerview:${Vers.recyclerview}"

    const val swipe_refreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Vers.swipe_refreshlayout}"

}
