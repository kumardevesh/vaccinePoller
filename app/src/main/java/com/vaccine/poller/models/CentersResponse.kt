package com.vaccine.poller.models

import com.google.gson.annotations.SerializedName

data class CentersResponse(
    @SerializedName("centers")
    val centers: ArrayList<Center>
)

data class Center(
    @SerializedName("center_id")
    val centerId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("sessions")
    val sessions: List<Session>,
    @SerializedName("pincode")
    val pincode: Long
)


data class Session(
    @SerializedName("available_capacity")
    val capacity: Int,
    @SerializedName("min_age_limit")
    val minAge: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("slots")
    val slots: ArrayList<String>
)