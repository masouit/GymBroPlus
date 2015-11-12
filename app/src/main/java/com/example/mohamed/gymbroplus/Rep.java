package com.example.mohamed.gymbroplus;

/**
 * Created by Mohamed on 12-11-2015.
 * Rep
 */
public class Rep {
    private int _repid,_repamount;

    // constructors
    public Rep() {
    }

    public Rep(int repamount){
        _repamount = repamount;
    }

    public Rep(int id,int repamount){
        _repid = id;
        _repamount = repamount;
    }

    // setters
    public void setRepId(int id) {
        _repid = id;
    }

    public void setRepAmount(int repamount) {
        _repamount = repamount;
    }

    // getters
    public long getRepId() {
        return _repid;
    }

    public long getRepAmount() {
        return _repamount;
    }
}
