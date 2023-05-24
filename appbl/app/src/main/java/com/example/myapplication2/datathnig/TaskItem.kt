package com.example.myapplication2.datathnig

import android.os.Parcel
import android.os.Parcelable

data class TaskItem(
    var name: String,
    var isChecked: Boolean = false,
    var id: Int? = null,
    var hidden: Boolean = false,
    var parentId: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeByte(if (isChecked) 1 else 0)
        parcel.writeValue(id)
        parcel.writeByte(if (hidden) 1 else 0)
        parcel.writeValue(parentId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskItem> {
        override fun createFromParcel(parcel: Parcel): TaskItem {
            return TaskItem(parcel)
        }

        override fun newArray(size: Int): Array<TaskItem?> {
            return arrayOfNulls(size)
        }
    }
}

