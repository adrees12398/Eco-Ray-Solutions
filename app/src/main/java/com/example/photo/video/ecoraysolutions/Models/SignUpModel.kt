package com.example.photo.video.ecoraysolutions.Models

import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.Date

data class SignUpModel(
    var id: String? = null,
    val name: String? = null,
    val father_name: String? = null,
    val email: String? = null,
    val Birth_of_Date: String? = null,
    val address: String? = null,
    val password: String? = null,
    val phoneNo: String? = null,
    val city: String? = null,
    val type: String? = null,
    val accountStatus: Boolean = false,
    @ServerTimestamp
    var timestamp: Date? = null
) : Serializable
