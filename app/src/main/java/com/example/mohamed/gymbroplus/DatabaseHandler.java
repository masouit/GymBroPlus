package com.example.mohamed.gymbroplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 5-11-2015.
 * DatabaseHandler
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME = "GymBro";

    // table details
    public String tableName = "Exercise";

    public String exerciseId = "id";
    public String exerciseName = "exercisename";
    public String exerciseClientName = "clientname";
    public String exerciseDay = "day";
    public String exerciseSet = "set";
    public String exerciseReps = "reps";
    public String exerciseWeight = "weight";

    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";

        sql += "CREATE TABLE " + tableName;
        sql += " ( ";
        sql += exerciseId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += exerciseName + " TEXT, ";
        sql += exerciseClientName + " TEXT ";
        sql += exerciseDay + " INTEGER, ";
        sql += exerciseSet + " INTEGER ";
        sql += exerciseReps + " INTEGER, ";
        sql += exerciseWeight + " INTEGER ";
        sql += " ) ";

        db.execSQL(sql);
    }

    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);

        onCreate(db);
    }

    //Create Exercise
    public void createExercise(Exercise exercise){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(exerciseName, exercise.getExercisename());
        values.put(exerciseClientName, exercise.getClientname());
        values.put(exerciseDay, exercise.getDay());
        values.put(exerciseSet, exercise.getSet());
        values.put(exerciseReps, exercise.getReps());
        values.put(exerciseWeight, exercise.getWeight());

        db.insert(tableName, null, values);
        db.close();
    }

    //Get Exercise
    public Exercise getExercise(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(tableName,
                new String[]{
                        exerciseId,
                        exerciseName,
                        exerciseClientName,
                        exerciseDay,
                        exerciseSet,
                        exerciseReps,
                        exerciseWeight
                },
                exerciseId + "=?",
                new String[]{String.valueOf(id)},
                null,null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        Exercise exercise = new Exercise(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6))
        );
        db.close();
        cursor.close();
        return exercise;
    }

    //Delete Exercise
    public void deleteExercise(Exercise exercise){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tableName,exerciseId + "=?",new String[]{ String.valueOf(exercise.getId()) });
        db.close();
    }

    //Get Exercise count
    public int getExerciseCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

    //Update Exercise
    public int updateExercise(Exercise exercise){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(exerciseName, exercise.getExercisename());
        values.put(exerciseClientName, exercise.getClientname());
        values.put(exerciseDay, exercise.getDay());
        values.put(exerciseSet, exercise.getSet());
        values.put(exerciseReps, exercise.getReps());
        values.put(exerciseWeight, exercise.getWeight());

        return db.update(tableName, values, exerciseId + "=?", new String[]{String.valueOf(exercise.getId())});
    }

    //Get all Exercises
    public List<Exercise> getAllExercises(){
        List<Exercise> exercises = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if (cursor.moveToFirst()){
            do{
                Exercise exercise = new Exercise(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6))
                );
                exercises.add(exercise);
            }
            while(cursor.moveToNext());
        }
        return exercises;
    }

}
