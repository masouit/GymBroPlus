package com.example.mohamed.gymbroplus;

/**
 * Created by Mohamed on 22-11-2015.
 *
 *      DAY_EXERCISE
 PK	    day_ex_id
 FK2	bpday_id
        ordernumber
        exerciseId
 *
 */
public class DayExercise {
    private int _dayexid,_bpdayid,_ordernumber,_exerciseId;

    // constructors
    public DayExercise() {
    }

    public DayExercise(int bpdayid, int ordernumber, int exerciseid){
        _bpdayid = bpdayid;
        _ordernumber = ordernumber;
        _exerciseId = exerciseid;
    }

    public DayExercise(int id, int bpdayid, int ordernumber, int exerciseid){
        _dayexid = id;
        _bpdayid = bpdayid;
        _ordernumber = ordernumber;
        _exerciseId = exerciseid;
    }

    // setters
    public void setDayExId(int id) { _dayexid = id; }

    public void setBpDayId(int bpdayid) { _bpdayid = bpdayid; }

    public void setOrdernumber(int ordernumber) { _ordernumber = ordernumber; }

    public void setExersieId(int repamount) {
        _exerciseId = repamount;
    }

    // getters
    public int getDayExId() {
        return _dayexid;
    }

    public int getBpDayId() {
        return _bpdayid;
    }

    public int getOrdernumber() { return _ordernumber; }

    public int getExersieId() { return _exerciseId; }
}
