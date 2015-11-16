package com.example.mohamed.gymbroplus;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed on 5-11-2015.
 * Exercise
 */
public class Exercise implements Parcelable {
    private int _exerciseid;
    private String _exercisename;
    //parceable settings
    private Exercise(Parcel in) {
        _exerciseid = in.readInt();
        _exercisename = in.readString();

    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(_exerciseid);
        dest.writeString(_exercisename);

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


    public Exercise(String exercisename){
        _exercisename = exercisename;
    }

    public Exercise(int id,String exercisename){
        _exerciseid = id;
        _exercisename = exercisename;
    }

    // setters
    public void setExerciseId(int id) {
        _exerciseid = id;
    }

    public void setExerciseName(String exercisename) {
        _exercisename = exercisename;
    }

    // getters
    public int getExerciseId() {
        return _exerciseid;
    }

    public String getExerciseName() {
        return _exercisename;
    }



}
