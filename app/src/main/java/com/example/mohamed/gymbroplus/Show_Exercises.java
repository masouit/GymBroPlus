package com.example.mohamed.gymbroplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.ArrayList;
import java.util.List;

public class Show_Exercises extends AppCompatActivity {
    DatabaseHandler db;
    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    List<Rep> reps = new ArrayList<Rep>();

    ExpandableListExerciseAdapter listAdapter;
    ExpandableListView expListView;
    List<Exercise> listDataHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__exercises);
        setTitle("Show Exercises");

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);


        //set indicator to right
        setGroupIndicatorToRight();

        // preparing list data
        prepareListData();

        // Check for ExpandableListAdapter object
        if (listAdapter == null)
        {
            //Create ExpandableListAdapter Object
            listAdapter = new ExpandableListExerciseAdapter(listDataHeader,this);

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
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

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
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : ", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

//        ImageView delete = (ImageView) expListView.findViewById(R.id.delete);
//        delete.setOnClickListener(new ExpandableListView.OnClickListener() {
//
//            public void onClick(final View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
//                builder.setMessage("Do you want to remove?");
//                builder.setCancelable(false);
//                builder.setPositiveButton("Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                int groupPosition = ExpandableListView.getPackedPositionGroup(id);
//                                String group = listDataHeader.get(groupPosition);
//                                listDataHeader.remove(groupPosition);
//                                listAdapter.notifyDataSetChanged();
//                                Toast.makeText(getBaseContext(), " Deleted", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                builder.setNegativeButton("No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Log.i("", "Click");
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

        // Show context menu for groups
        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            menu.setHeaderTitle("Group");
            menu.add(0, 0, 1, "Delete");
            menu.add(0,1,2,"Edit");

            // Show context menu for children
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            //menu.setHeaderTitle("Child");
            //menu.add(0, 0, 1, "Delete");
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item
                .getMenuInfo();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);
        int menuItem = item.getItemId();

        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            // do something with parent
            //TODO contextmenu parent
//            Toast.makeText(
//                    getApplicationContext(),
//                    listDataHeader.get(groupPosition)
//                    , Toast.LENGTH_SHORT)
//                    .show();
            if(menuItem==0){//delete
                Toast.makeText(
                        getApplicationContext(),
                        "DELETE"
                        , Toast.LENGTH_SHORT)
                        .show();
            }else if(menuItem==1){//edit
                editExercise(exercises.get(groupPosition),groupPosition);
            }

        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            // do someting with child
            //TODO contextmenu child
        }

        return super.onContextItemSelected(item);
    }
    public void editExercise(Exercise exercise,int exerciseId){

        Intent intent = new Intent(this, Edit_Exercise.class);
        intent.putExtra("exerciselist", exercises);
        intent.putExtra("exerciseId",exerciseId);
        startActivity(intent);

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
        listDataHeader = new ArrayList<Exercise>();
        db = new DatabaseHandler(getApplicationContext());
        exercises.addAll(db.getAllExercises());

        for (int i = 0; i < exercises.size(); i++) {
            ArrayList<Rep> list = new ArrayList<Rep>();

            reps.addAll(db.getAllRepsByExercise(exercises.get(i).getExerciseId()));
            for (int j = 0; j < reps.size(); j++){
                list.add(reps.get(j));
            }
            exercises.get(i).setExerciseReps(list);
            reps.clear();
        }
        listDataHeader.addAll(exercises);
        db.closeDB();
    }
}
