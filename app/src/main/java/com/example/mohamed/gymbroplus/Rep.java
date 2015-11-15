package com.example.mohamed.gymbroplus;

/**
 * Created by Mohamed on 12-11-2015.
 * Rep
 */
public class Rep {
    private long _repid,_repamount,_exerciseid;

    // constructors
    public Rep() {
    }

    public Rep(long exerciseid, int repamount){
        _exerciseid = exerciseid;
        _repamount = repamount;
    }

    public Rep(long id, long exerciseid, int repamount){
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
    public long getRepId() {
        return _repid;
    }

    public long getExerciseId() {
        return _exerciseid;
    }

    public long getRepAmount() {
        return _repamount;
    }
}
