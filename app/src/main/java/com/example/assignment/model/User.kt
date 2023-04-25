package com.example.assignment.model

import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val telNum: String
        ): Parcelable