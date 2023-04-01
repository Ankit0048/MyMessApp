package `in`.day1.mymessapp.Activity.Models

import android.os.Parcel
import android.os.Parcelable

data class History(
    val BREAKFAST: Int = 0,
    val LUNCH: Int = 0,
    val SNACKS: Int = 0,
    val DINNER: Int = 0,
    val PAID: Int = 0,
    val reviewedBREAKFAST: Int = 0,
    val reviewedLUNCH: Int = 0,
    val reviewedSNACKS: Int= 0,
    val reviewedDINNER: Int = 0,
    val tookBREAKFAST: Int = 0,
    val tookLUNCH: Int = 0,
    val tookDINNER: Int = 0,
    val tookSNACKS: Int = 0
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
        parcel.writeInt(BREAKFAST)
        parcel.writeInt(LUNCH)
        parcel.writeInt(SNACKS)
        parcel.writeInt(DINNER)
        parcel.writeInt(PAID)
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
