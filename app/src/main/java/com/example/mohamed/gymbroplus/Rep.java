package com.example.mohamed.gymbroplus;

/**
 * Created by Mohamed on 12-11-2015.
 * Rep
 */
public class Rep {
    private int _repid,_repamount,_exerciseid;

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
