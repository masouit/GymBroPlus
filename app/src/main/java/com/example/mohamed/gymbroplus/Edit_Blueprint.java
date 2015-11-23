package com.example.mohamed.gymbroplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Edit_Blueprint extends AppCompatActivity {
    ExpandableListView expListView;
    int blueprintId;
    DatabaseHandler db;
    List<Blueprint> listDataHeader;
    ArrayList<Blueprint> blueprints;
    List<BlueprintDay> days;
    ExpandableListBlueprintAdapter listAdapter;

    String blueprintname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__blueprint);


        blueprintId = getIntent().getIntExtra("blueprintId",0);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListViewBlueprint);
        prepareListData();

        // Check for ExpandableListAdapter object
        if (listAdapter == null) {
            //Create ExpandableListAdapter Object
            listAdapter = new ExpandableListBlueprintAdapter(listDataHeader, this);

            // Set Adapter to ExpandableList Adapter
            expListView.setAdapter(listAdapter);
        }
        else {
            // Refresh ExpandableListView data
            listAdapter.notifyDataSetChanged();
        }

        registerForContextMenu(expListView);
        expListView.expandGroup(0);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return parent.isGroupExpanded(groupPosition);
            }
        });
        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getBaseContext(), Edit_day.class);
                intent.putExtra("dayId",childPosition);
                startActivity(intent);
                return false;
            }
        });
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<Blueprint>();
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
        listDataHeader.addAll(blueprints);
        db.closeDB();
    }
}
