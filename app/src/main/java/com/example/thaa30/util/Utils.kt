package com.example.thaa30.util

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Talker(
    var whoSpeake: String = "man",
    var taking: String = "tadam",
    var takingArray: ArrayList<String> = arrayListOf(),
    var styleNum: Int = 0,
    var animNum: Int = 0,
    var dur: Long = 1000,
    var textSize: Float = 28f,
    var colorBack: String = "none",
    var backExist: Boolean = true,
    var colorText: String = "#ffffff",
    var numTalker: Int = 0,
    var radius: Float = 30f,
    var padding: ArrayList<Int> = arrayListOf(10, 0, 10, 0),
    var borderColor: String = "#000000",
    var borderWidth: Int = 20,
    var swingRepeat: Int = 0
) : Serializable



class StyleObject(
    val numStyleObject: Int = 0,
    val colorBack: String = "none",
    val colorText: String = "#ffffff",
    val sizeText: Float = 20f,
    val fontText: Int = 10
) : Serializable

data class Convers(
    val numC: Int? = null,
    val title: String? = null,
    val explanation: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(numC)
        parcel.writeString(title)
        parcel.writeString(explanation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Convers> {
        override fun createFromParcel(parcel: Parcel): Convers {
            return Convers(parcel)
        }

        override fun newArray(size: Int): Array<Convers?> {
            return arrayOfNulls(size)
        }
    }
}