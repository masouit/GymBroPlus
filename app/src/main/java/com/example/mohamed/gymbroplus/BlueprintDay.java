package com.example.mohamed.gymbroplus;

/**
 * Created by Mohamed on 22-11-2015.
 *
 *      BLUEPRINT_DAY
 PK,FK2 bpday_id
 FK1	blueprint_id
        bpday_number
 *
 */
public class BlueprintDay {
    private int _bpdayid,_blueprintid,_bpdaynumber;

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
