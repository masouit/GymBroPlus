package com.example.mohamed.gymbroplus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Show_Blueprints extends AppCompatActivity {
    DatabaseHandler db;
    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    List<Rep> reps = new ArrayList<Rep>();

    ExpandableListBlueprintAdapter listAdapter;
    ExpandableListView expListView;
    List<Blueprint> listDataBlueprint;

    ArrayList<Blueprint> blueprints = new ArrayList<Blueprint>();
    List<BlueprintDay> days = new ArrayList<BlueprintDay>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__blueprints);
        setTitle("Show Blueprints");

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListViewblue);


        //set indicator to right
        setGroupIndicatorToRight();

        // preparing list data
        prepareListData();

        // Check for ExpandableListAdapter object
        if (listAdapter == null)
        {
            //Create ExpandableListAdapter Object
            listAdapter = new ExpandableListBlueprintAdapter(listDataBlueprint, this);

            // Set Adapter to ExpandableList Adapter
            expListView.setAdapter(listAdapter);
        }
        else
        {
            // Refresh ExpandableListView data
            listAdapter.notifyDataSetChanged();
        }

        registerForContextMenu(expListView);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Intent intent = new Intent(getBaseContext(), Edit_day.class);
//                intent.putExtra("dayId",childPosition);
//                startActivity(intent);
                return false;
            }
        });
    }

    /*
     * Set groupindicator to the right
     */
    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBoundsRelative(width - getDipsFromPixel(60), width
                - getDipsFromPixel(40));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale );
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataBlueprint = new ArrayList<Blueprint>();
        db = new DatabaseHandler(getApplicationContext());
        blueprints.addAll(db.getAllBlueprints());

        for (int i = 0; i < blueprints.size(); i++) {
            ArrayList<BlueprintDay> list = new ArrayList<BlueprintDay>();

            days.addAll(db.getAllBlueprintDaysByBlueprint(blueprints.get(i).getBlueprintId()));
            for (int j = 0; j < days.size(); j++){
                list.add(days.get(j));
            }
            blueprints.get(i).setExerciseReps(list);
            days.clear();
        }
        listDataBlueprint.addAll(blueprints);
        db.closeDB();
    }

    public void delBlueprint(Blueprint blueprint,boolean deletedays,Context context){
        db = new DatabaseHandler(context);
        db.deleteBlueprint(blueprint,deletedays);
        db.closeDB();
    }
}
