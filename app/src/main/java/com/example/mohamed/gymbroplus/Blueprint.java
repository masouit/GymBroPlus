package com.example.mohamed.gymbroplus;

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
public class Blueprint {
    private int _blueprintid;
    private String _blueprintname;
    private List<BlueprintDay> dayList = new ArrayList<BlueprintDay>();

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
    public void setExerciseReps(List<BlueprintDay> dayList) {
        this.dayList = dayList;
    }

}
