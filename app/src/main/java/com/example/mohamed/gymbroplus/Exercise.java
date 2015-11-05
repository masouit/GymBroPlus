package com.example.mohamed.gymbroplus;

/**
 * Created by Mohamed on 5-11-2015.
 */
public class Exercise {
    private int _id,_day,_set,_reps,_weight;
    private String _exercisename,_clientname;

    public Exercise(int id, String exercisename, String clientname, int day, int set, int reps, int weight){
        _id = id;
        _exercisename = exercisename;
        _clientname = clientname;
        _day =  day;
        _set = set;
        _reps = reps;
        _weight = weight;
    }

    public int getId(){return _id;}

    public String getExercisename(){return _exercisename;}

    public String getClientname(){return _clientname;}

    public int getDay(){return _day;}

    public int getSet(){return _set;}

    public int getReps(){return _reps;}

    public int getWeight(){return _weight;}

}
