package com.example.mohamed.gymbroplus;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 22-11-2015.
 *
 *      BLUEPRINT
 PK,FK1	blueprint_id
        blueprintname
 *
 */
public class Blueprint implements Parcelable {
    private int _blueprintid;
    private String _blueprintname;
    private List<BlueprintDay> dayList = new ArrayList<BlueprintDay>();

    //parceable settings
    private Blueprint(Parcel in) {
        _blueprintid = in.readInt();
        _blueprintname = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(_blueprintid);
        dest.writeString(_blueprintname);

    }

    public static final Parcelable.Creator<Blueprint> CREATOR = new Parcelable.Creator<Blueprint>() {
        public Blueprint createFromParcel(Parcel in) {
            return new Blueprint(in);
        }

        public Blueprint[] newArray(int size) {
            return new Blueprint[size];

        }
    };
    // constructors
    public Blueprint() {
    }


    public Blueprint(String blueprintname){
        _blueprintname = blueprintname;
    }

    public Blueprint(int blueprintid, String blueprintname){
        _blueprintid = blueprintid;
        _blueprintname = blueprintname;
    }

    // setters
    public void setBlueprintId(int id) { _blueprintid = id; }

    public void setBlueprintName(String name) { _blueprintname = name; }


    // getters
    public int getBlueprintId() {
        return _blueprintid;
    }

    public String getBlueprintName() { return _blueprintname; }
    public List<BlueprintDay> getdayList() { return dayList; }
    public void setBlueprintDays(List<BlueprintDay> dayList) {
        this.dayList = dayList;
    }

}
