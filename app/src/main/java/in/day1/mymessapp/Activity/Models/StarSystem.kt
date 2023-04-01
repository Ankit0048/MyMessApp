package `in`.day1.mymessapp.Activity.Models

import android.os.Parcel
import android.os.Parcelable

data class StarSystem(
    val one : Int = 0,
    val two : Int = 0,
    val three: Int = 0,
    val four : Int = 0,
    val five : Int = 0,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()

    )
    override fun describeContents(): Int {

        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(one)
        dest.writeInt(two)
        dest.writeInt(three)
        dest.writeInt(four)
        dest.writeInt(five)

    }

    companion object CREATOR : Parcelable.Creator<StarSystem> {
        override fun createFromParcel(parcel: Parcel): StarSystem {
            return StarSystem(parcel)
        }

        override fun newArray(size: Int): Array<StarSystem?> {
            return arrayOfNulls(size)
        }
    }

}
