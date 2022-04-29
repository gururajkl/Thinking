package com.example.thinking.models

import android.os.Parcel
import android.os.Parcelable

data class User (
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val usn: String = "",
    val image: String = "",
    val phone: Long = 0,
    val fcmToken: String = ""
): Parcelable {  // Parcelable used to use the data of the data class in another activity or class
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(usn)
        parcel.writeString(image)
        parcel.writeLong(phone)
        parcel.writeString(fcmToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}