package com.example.mohamed.gymbroplus;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 5-11-2015.
 * Exercise
 */
public class Exercise implements Parcelable {
    private int _exerciseid;
    private String _exercisename,_groupname;
    private List<Rep> repList = new ArrayList<Rep>();

    //parceable settings
    private Exercise(Parcel in) {
        _exerciseid = in.readInt();
        _exercisename = in.readString();
        _groupname = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(_exerciseid);
        dest.writeString(_exercisename);
        dest.writeString(_groupname);

    }

    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];

        }
    };

    // all get , set method

    // constructors
    public Exercise() {
    }


    public Exercise(String exercisename,String groupname){
        _exercisename = exercisename;
        _groupname = groupname;
    }

    public Exercise(int id,String exercisename,String groupname){
        _exerciseid = id;
        _exercisename = exercisename;
        _groupname = groupname;
    }

    // setters
    public void setExerciseId(int id) {
        _exerciseid = id;
    }

    public void setExerciseName(String exercisename) {
        _exercisename = exercisename;
    }

    public void setGroupName(String groupname) {
        _groupname = groupname;
    }

    // getters
    public int getExerciseId() {
        return _exerciseid;
    }

    public String getExerciseName() {
        return _exercisename;
    }

    public String getGroupName(){ return _groupname; }

    public List<Rep> getRepList() { return repList; }
    public void setExerciseReps(List<Rep> repList) {
        this.repList = repList;
    }

}
