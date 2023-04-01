package `in`.day1.mymessapp.Activity.Models

import android.os.Parcel
import android.os.Parcelable

data class History(
    val breakfast: Int = 0,
    val lunch: Int = 0,
    val snacks: Int = 0,
    val dinner: Int = 0,
    val paid: Int = 0,
    val reviewedBREAKFAST: Int = 0,
    val reviewedLUNCH: Int = 0,
    val reviewedSNACKS: Int= 0,
    val reviewedDINNER: Int = 0,
    var tookBREAKFAST: Int = 0,
    var tookLUNCH: Int = 0,
    var tookDINNER: Int = 0,
    var tookSNACKS: Int = 0
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
        )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(breakfast)
        parcel.writeInt(lunch)
        parcel.writeInt(snacks)
        parcel.writeInt(dinner)
        parcel.writeInt(paid)
        parcel.writeInt(reviewedBREAKFAST)
        parcel.writeInt(reviewedDINNER)
        parcel.writeInt(reviewedLUNCH)
        parcel.writeInt(reviewedSNACKS)
        parcel.writeInt(tookBREAKFAST)
        parcel.writeInt(tookLUNCH)
        parcel.writeInt(tookSNACKS)
        parcel.writeInt(tookDINNER)

    }

    companion object CREATOR : Parcelable.Creator<History> {
        override fun createFromParcel(parcel: Parcel): History {
            return History(parcel)
        }

        override fun newArray(size: Int): Array<History?> {
            return arrayOfNulls(size)
        }
    }

}
