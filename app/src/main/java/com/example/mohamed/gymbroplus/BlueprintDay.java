package com.example.mohamed.gymbroplus;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 22-11-2015.
 *
 *      BLUEPRINT_DAY
 PK,FK2 bpday_id
 FK1	blueprint_id
        bpday_number
 *
 */
public class BlueprintDay implements Parcelable {
    private int _bpdayid,_blueprintid,_bpdaynumber;

    //parceable settings
    private BlueprintDay(Parcel in) {
        _bpdayid      = in.readInt();
        _blueprintid  = in.readInt();
        _bpdaynumber = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_bpdayid);
        dest.writeInt(_blueprintid);
        dest.writeInt(_bpdaynumber);
    }

    public static final Parcelable.Creator<BlueprintDay> CREATOR = new Parcelable.Creator<BlueprintDay>() {
        public BlueprintDay createFromParcel(Parcel in) {
            return new BlueprintDay(in);
        }

        public BlueprintDay[] newArray(int size) {
            return new BlueprintDay[size];
        }
    };

    // constructors
    public BlueprintDay() {
    }

    public BlueprintDay(int exerciseid, int repamount){
        _blueprintid = exerciseid;
        _bpdaynumber = repamount;
    }

    public BlueprintDay(int id, int exerciseid, int repamount){
        _bpdayid = id;
        _blueprintid = exerciseid;
        _bpdaynumber = repamount;
    }

    // setters
    public void setBlueprintDayId(int id) { _bpdayid = id; }

    public void setBlueprintId(int id) { _blueprintid = id; }

    public void setBlueprintDayNumber(int bpdaynumber) {
        _bpdaynumber = bpdaynumber;
    }

    // getters
    public int getBlueprintDayId() {
        return _bpdayid;
    }

    public int getBlueprintId() {
        return _blueprintid;
    }

    public int getBlueprintDayNumber() { return _bpdaynumber; }

}
