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
    protected static final String DATABASE_NAME     = "GymBro";

    // table names
    private static final String TABLE_Exercise      = "Exercise";
    private static final String TABLE_Rep           = "Rep";
    private static final String TABLE_Exercise_Rep  = "Exercise_Rep";

    // Common column names
    private static final String KEY_exerciseId      = "exerciseId";
    private static final String KEY_repId           = "repId";

    // table Exercise column names
    private static final String KEY_exerciseName    = "exerciseName";

    // table Rep column names
    private static final String KEY_repAmount       = "repAmount";

    // Table Create Statements
    // Exercise table create statement
    private static final String CREATE_TABLE_Exercise =
            "CREATE TABLE " + TABLE_Exercise + "(" +
                    KEY_exerciseId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_exerciseName + " TEXT" + ")";

    // Rep table create statement
    private static final String CREATE_TABLE_Rep =
            "CREATE TABLE " + TABLE_Rep + "(" +
                    KEY_repId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_repAmount + " INTEGER" + ")";

    // Exercise_Rep table create statement
    private static final String CREATE_TABLE_Exercise_Rep =
            "CREATE TABLE " + TABLE_Exercise_Rep + "(" +
                    KEY_exerciseId + " INTEGER PRIMARY KEY," +
                    KEY_repId + " INTEGER," +
                    "UNIQUE (" + KEY_exerciseId + ", " + KEY_repId + ")" + ")";

    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_Exercise);
        db.execSQL(CREATE_TABLE_Rep);
        db.execSQL(CREATE_TABLE_Exercise_Rep);
    }

    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Exercise);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Rep);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Exercise_Rep);

        onCreate(db);
    }

    /**
     * Exercise
     */

    //Create Exercise
    public long createExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_exerciseName, exercise.getExerciseName());

        // insert row
        long exercise_id = db.insert(TABLE_Exercise, null, values);

        return exercise_id;
    }

    //GetAll Exercise
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<Exercise>();
        String selectQuery = "SELECT  * FROM " + TABLE_Exercise;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Exercise t = new Exercise();
                t.setExerciseId(c.getInt((c.getColumnIndex(KEY_exerciseId))));
                t.setExerciseName(c.getString(c.getColumnIndex(KEY_exerciseName)));

                // adding to cats list
                exercises.add(t);
            } while (c.moveToNext());
        }
        return exercises;
    }

    //Update Exercise
    public int updateExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_exerciseName, exercise.getExerciseName());

        // updating row
        return db.update(TABLE_Exercise, values, KEY_exerciseId + " = ?",
                new String[] { String.valueOf(exercise.getExerciseId()) });
    }

    //Delete Exercise (will delete reps first)
    public void deleteExercise(Exercise exercise, boolean should_delete_all_exercise_reps) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting cat
        // check if tasks under this cat should also be deleted
        if (should_delete_all_exercise_reps) {
            // get all tasks under this cat
            List<Rep> allexercisereps = getAllRepsByExercise(exercise.getExerciseName());

            // delete all tasks
            for (Rep rep : allexercisereps) {
                // delete task
                deleteRep(rep.getRepId());
            }
        }

        // now delete the cat
        db.delete(TABLE_Exercise, KEY_exerciseId + " = ?",
                new String[] { String.valueOf(exercise.getExerciseId()) });
    }

    /**
     * Rep
     */

    //Create Rep
    public long createRep(Rep rep, long[] exercise_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_repAmount, rep.getRepAmount());

        // insert row
        long rep_id = db.insert(TABLE_Rep, null, values);

        // assigning Rep to Exercise
        for (long exercise_id : exercise_ids) {
            createExerciseRep(rep_id, exercise_id);
        }

        return rep_id;
    }

    //Get Rep
    public Rep getRep(long rep_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_Rep + " WHERE "
                + KEY_repId + " = " + rep_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Rep td = new Rep();
        td.setRepId(c.getInt(c.getColumnIndex(KEY_repId)));
        td.setRepAmount((c.getInt(c.getColumnIndex(KEY_repAmount))));

        return td;
    }

    //Get All Reps
    public List<Rep> getAllReps() {
        List<Rep> reps = new ArrayList<Rep>();
        String selectQuery = "SELECT  * FROM " + TABLE_Rep;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Rep td = new Rep();
                td.setRepId(c.getInt(c.getColumnIndex(KEY_repId)));
                td.setRepAmount((c.getInt(c.getColumnIndex(KEY_repAmount))));

                // adding to task list
                reps.add(td);
            } while (c.moveToNext());
        }

        return reps;
    }

    //Get All Reps from Exercise
    public List<Rep> getAllRepsByExercise(String exercise_name) {
        List<Rep> tasks = new ArrayList<Rep>();

        String selectQuery = "SELECT  * FROM " + TABLE_Rep + " td, "
                + TABLE_Exercise + " tg, " + TABLE_Exercise_Rep + " tt WHERE tg."
                + KEY_exerciseName + " = '" + exercise_name + "'" + " AND tg." + KEY_exerciseId
                + " = " + "tt." + KEY_exerciseId + " AND td." + KEY_repId + " = "
                + "tt." + KEY_repId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Rep td = new Rep();
                td.setRepId(c.getInt(c.getColumnIndex(KEY_repId)));
                td.setRepAmount((c.getInt(c.getColumnIndex(KEY_repAmount))));

                // adding to task list
                tasks.add(td);
            } while (c.moveToNext());
        }

        return tasks;
    }

    //Update Rep
    public int updateRep(Rep rep) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_repAmount, rep.getRepAmount());

        // updating row
        return db.update(TABLE_Rep, values, KEY_repId + " = ?",
                new String[] { String.valueOf(rep.getRepId()) });
    }

    //Delete Rep
    public void deleteRep(long rep_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_Rep, KEY_repId + " = ?",
                new String[] { String.valueOf(rep_id) });
    }

    /**
     * Creating Exercise_Rep
     */
    //Create ExerciseRep
    public long createExerciseRep(long exercise_id, long rep_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_exerciseId, exercise_id);
        values.put(KEY_repId, rep_id);

        long id = db.insert(TABLE_Exercise_Rep, null, values);

        return id;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
