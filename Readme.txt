** Title ""
IIT BBS MESS APP - "Android App"

** Descripiton **
This is a user admin app where the user of the institute can login and look into mess data and 
access and manage user account balance and meal consumption and much more

** REQUIREMENTS **
Internet connection is a must for the working of the app functionality
MinSDK 22 android for android that is Android 5



** Implementations for gradle and plugins **
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.5.1'
    implementation 'com.google.android.material:material:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    implementation 'com.google.firebase:firebase-auth:21.2.0'
    implementation 'com.google.firebase:firebase-firestore:24.4.5'
    implementation platform('com.google.firebase:firebase-bom:31.4.0')
    implementation 'com.google.android.gms:play-services-auth:20.4.1'
    implementation 'com.google.firebase:firebase-storage:19.1.1'

    implementation 'com.github.bumptech.glide:glide:4.15.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.15.0'

Plugins 
    id 'com.android.application' version '7.3.1' apply false
    id 'com.android.library' version '7.3.1' apply false
    id 'org.jetbrains.kotlin.android' version '1.7.20' apply false
    id 'com.google.gms.google-services' version '4.3.15' apply false


** PERMISSION NEEDED **
READ STORAGE PERMISSION
INTERNET
NETWORK STATE