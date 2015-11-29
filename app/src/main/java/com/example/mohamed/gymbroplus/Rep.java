package com.example.mohamed.gymbroplus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed on 12-11-2015.
 * Rep
 */
public class Rep implements Parcelable {
    private int _repid,_repamount,_exerciseid;

    //parceable settings
    private Rep(Parcel in) {
        _repid      = in.readInt();
        _repamount  = in.readInt();
        _exerciseid = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(_repid);
        dest.writeInt(_repamount);
        dest.writeInt(_exerciseid);

    }

    public static final Parcelable.Creator<Rep> CREATOR = new Parcelable.Creator<Rep>() {
        public Rep createFromParcel(Parcel in) {
            return new Rep(in);
        }

        public Rep[] newArray(int size) {
            return new Rep[size];

        }
    };

    // constructors
    public Rep() {
    }

    public Rep(int exerciseid, int repamount){
        _exerciseid = exerciseid;
        _repamount = repamount;
    }

    public Rep(int id, int exerciseid, int repamount){
        _repid = id;
        _exerciseid = exerciseid;
        _repamount = repamount;
    }

    // setters
    public void setRepId(int id) { _repid = id; }

    public void setExerciseId(int id) { _exerciseid = id; }

    public void setRepAmount(int repamount) {
        _repamount = repamount;
    }

    // getters
    public int getRepId() {
        return _repid;
    }

    public int getExerciseId() {
        return _exerciseid;
    }

    public int getRepAmount() {
        return _repamount;
    }
}
