package com.example.myapplication2.notification

import com.google.firebase.messaging.FirebaseMessagingService

class PushService: FirebaseMessagingService() {

    override fun onNewToken(Newtoken: String) {
        super.onNewToken(Newtoken)


    }
}