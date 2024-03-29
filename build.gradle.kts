import com.android.sdklib.repository.meta.DetailsTypes.AddonDetailsType.Libraries

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    id 'com.android.application' version '8.0.1' apply false
//    id 'com.android.library' version '8.0.1' apply false
//    id 'org.jetbrains.kotlin.android' version '1.7.20' apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
}