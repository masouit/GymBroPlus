package com.example.mohamed.gymbroplus;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Edit_day extends AppCompatActivity {;
    //ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    DatabaseHandler db;
    //List<Exercise> listDataExercise;

    //List<DayExercise> dayExercise;

    List<Exercise> items1, items2, items3;
    ListView listView1, listView2;
    GridView gridView3;
    ItemListAdapter myItemListAdapter1, myItemListAdapter2;
    ItemGridAdapter myItemGridAdapter3;
    LinearLayoutAbsListView area1, area2, area3;
//    TextView prompt;
    BlueprintDay blueprintday;

    //Used to resume original color in drop ended/exited
    int resumeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_day);
        setTitle("Edit Day");

        //Get objects from other intent
        Intent intent = this.getIntent();
        blueprintday = (BlueprintDay) intent.getParcelableExtra("dayId");

//        listView1 = (ListView)findViewById(R.id.listview1);
        listView2 = (ListView)findViewById(R.id.listview2);
        gridView3 = (GridView)findViewById(R.id.gridview3);

//        area1 = (LinearLayoutAbsListView)findViewById(R.id.pane1);
        area2 = (LinearLayoutAbsListView)findViewById(R.id.pane2);
        area3 = (LinearLayoutAbsListView)findViewById(R.id.pane3);
//        area1.setOnDragListener(myOnDragListener);
        area2.setOnDragListener(myOnDragListener);
        area3.setOnDragListener(myOnDragListener);
//        area1.setAbsListView(listView1);
        area2.setAbsListView(listView2);
        area3.setAbsListView(gridView3);

        initItems();
//        myItemListAdapter1 = new ItemListAdapter(this, items1);
        myItemListAdapter2 = new ItemListAdapter(this, items2);
        myItemGridAdapter3 = new ItemGridAdapter(this, items3);
//        listView1.setAdapter(myItemListAdapter1);
        listView2.setAdapter(myItemListAdapter2);
        gridView3.setAdapter(myItemGridAdapter3);

//        listView1.setOnItemClickListener(listOnItemClickListener);
        listView2.setOnItemClickListener(listOnItemClickListener);
        gridView3.setOnItemClickListener(listOnItemClickListener);

//        listView1.setOnItemLongClickListener(myOnItemLongClickListener);
        listView2.setOnItemLongClickListener(myOnItemLongClickListener);
        gridView3.setOnItemLongClickListener(myOnItemLongClickListener);



//        prompt = (TextView) findViewById(R.id.prompt);
//        // make TextView scrollable
//        prompt.setMovementMethod(new ScrollingMovementMethod());
//        //clear prompt area if LongClick
//        prompt.setOnLongClickListener(new View.OnLongClickListener(){
//
//            @Override
//            public boolean onLongClick(View v) {
//                prompt.setText("");
//                return true;
//            }});
        if (items2.size()<1){setBackground(true);}

        resumeColor  = Color.parseColor("#868dce");
    }
    public void setBackground(boolean setText){
        if(setText=true){
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#000000"));
        Bitmap bg = Bitmap.createBitmap(200, 400, Bitmap.Config.ARGB_8888);
        bg.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(bg);
        String text = "Drop ur shit here";
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(22);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;
        canvas.drawText(text, xPos, yPos, paint);
        area2.setBackgroundDrawable(new BitmapDrawable(bg));
        }else if(setText=false){

            Bitmap bg = Bitmap.createBitmap(200, 400, Bitmap.Config.ARGB_8888);
            bg.eraseColor(Color.WHITE);
            area2.setBackgroundDrawable(new BitmapDrawable(bg));
        }
    }

    public void saveDay(View view) {

        db = new DatabaseHandler(getApplicationContext());

        for (int i = 0; i < items2.size(); i++){

            DayExercise dayexercise = new DayExercise(blueprintday.getBlueprintDayId(),i, items2.get(i).getExerciseId());
            db.createDayExercise(dayexercise);
        }

        db.closeDB();
    }

    /*
     * Preparing the list data
     */
//    private void prepareListData() {
//        listDataExercise = new ArrayList<Exercise>();
//        db = new DatabaseHandler(getApplicationContext());
//
//        exercises.addAll(db.getAllExercises());
//        listDataExercise.addAll(exercises);
//
//        db.closeDB();
//    }
//    //items stored in ListView
//    public class Item {
//        Drawable ItemDrawable;
//        String ItemString;
//        Item(Drawable drawable, String t){
//            ItemDrawable = drawable;
//            ItemString = t;
//        }
//    }

    //objects passed in Drag and Drop operation
    class PassObject{
        View view;
        Exercise item;
        List<Exercise> srcList;

        PassObject(View v, Exercise i, List<Exercise> s){
            view = v;
            item = i;
            srcList = s;
        }
    }

    static class ViewHolder {
        ImageView icon;
        TextView text;
    }

    static class GridViewHolder {
        ImageView icon;
        TextView text;
    }

    public class ItemBaseAdapter extends BaseAdapter {

        Context context;
        List<Exercise> list;

        ItemBaseAdapter(Context c, List<Exercise> l){
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public List<Exercise> getList(){
            return list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

    }


    public class ItemListAdapter extends ItemBaseAdapter {

        ItemListAdapter(Context c, List<Exercise> l) {
            super(c, l);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // reuse views
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_group, null);

                ViewHolder viewHolder = new ViewHolder();
//                viewHolder.icon = (ImageView) rowView.findViewById(R.id.rowImageView);
                viewHolder.text = (TextView) rowView.findViewById(R.id.lblListHeader);
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
//            holder.icon.setImageDrawable(list.get(position).ItemDrawable);
            holder.text.setText(list.get(position).getExerciseName());

            rowView.setOnDragListener(new ItemOnDragListener(list.get(position)));

            return rowView;
        }

    }

    public class ItemGridAdapter extends ItemBaseAdapter {

        ItemGridAdapter(Context c, List<Exercise> l) {
            super(c, l);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View gridrowView = convertView;

            // reuse views
            if (gridrowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                gridrowView = inflater.inflate(R.layout.gridrow, null);

                GridViewHolder gridviewHolder = new GridViewHolder();
                gridviewHolder.icon = (ImageView) gridrowView.findViewById(R.id.gridrowImageView);
                gridviewHolder.text = (TextView) gridrowView.findViewById(R.id.grid_text);
                gridrowView.setTag(gridviewHolder);
            }

            GridViewHolder holder = (GridViewHolder) gridrowView.getTag();
            holder.icon.setImageDrawable(setGroupIcon(list.get(position).getGroupName()));
            holder.text.setText(list.get(position).getExerciseName());

            gridrowView.setOnDragListener(new ItemOnDragListener(list.get(position)));

            return gridrowView;
        }

    }
    public Drawable setGroupIcon(String _groupname){
        Drawable groupicon=null;
        switch(_groupname) {
            case "Abs":
                groupicon =getResources().getDrawable(R.drawable.checkbox_imageview_abs);
                break;
            case "Arms":
                groupicon =getResources().getDrawable(R.drawable.checkbox_imageview_arms);
                break;
            case "Back":
                groupicon =getResources().getDrawable(R.drawable.checkbox_imageview_back);
                break;
            case "Chest":
                groupicon =getResources().getDrawable(R.drawable.checkbox_imageview_chest);
                break;
            case "Legs":
                groupicon =getResources().getDrawable(R.drawable.checkbox_imageview_legs);
                break;
            case "Shoulders":
                groupicon =getResources().getDrawable(R.drawable.checkbox_imageview_shoulder);
                break;
        }
        return groupicon;
    }



    AdapterView.OnItemLongClickListener myOnItemLongClickListener = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            Exercise selectedItem = (Exercise)(parent.getItemAtPosition(position));

            ItemBaseAdapter associatedAdapter = (ItemBaseAdapter)(parent.getAdapter());
            List<Exercise> associatedList = associatedAdapter.getList();

            PassObject passObj = new PassObject(view, selectedItem, associatedList);

            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, passObj, 0);

            return true;
        }

    };

    View.OnDragListener myOnDragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            String area;
            if(v == area1){
                area = "area1";
            }else if(v == area2){
                area = "area2";
            }else if(v == area3){
                area = "area3";
            }else{
                area = "unknown";
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
//                    prompt.append("ACTION_DRAG_STARTED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    prompt.append("ACTION_DRAG_ENTERED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
//                    prompt.append("ACTION_DRAG_EXITED: " + area  + "\n");
                    break;
                case DragEvent.ACTION_DROP:
//                    prompt.append("ACTION_DROP: " + area  + "\n");

                    PassObject passObj = (PassObject)event.getLocalState();
                    View view = passObj.view;
                    Exercise passedItem = passObj.item;
                    List<Exercise> srcList = passObj.srcList;
                    AbsListView oldParent = (AbsListView)view.getParent();
                    ItemBaseAdapter srcAdapter = (ItemBaseAdapter)(oldParent.getAdapter());

                    LinearLayoutAbsListView newParent = (LinearLayoutAbsListView)v;
                    ItemBaseAdapter destAdapter = (ItemBaseAdapter)(newParent.absListView.getAdapter());
                    List<Exercise> destList = destAdapter.getList();

                    if(removeItemToList(srcList, passedItem)){
                        addItemToList(destList, passedItem);
                    }

                    srcAdapter.notifyDataSetChanged();
                    destAdapter.notifyDataSetChanged();

                    //smooth scroll to bottom
                    newParent.absListView.smoothScrollToPosition(destAdapter.getCount()-1);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
//                    prompt.append("ACTION_DRAG_ENDED: " + area  + "\n");
                default:
                    break;
            }

            return true;
        }

    };

    class ItemOnDragListener implements View.OnDragListener {

        Exercise  me;

        ItemOnDragListener(Exercise i){
            me = i;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
//                    prompt.append("Item ACTION_DRAG_STARTED: " + "\n");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    prompt.append("Item ACTION_DRAG_ENTERED: " + "\n");
                    v.setBackgroundColor(0x30000000);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
//                    prompt.append("Item ACTION_DRAG_EXITED: " + "\n");
                    v.setBackgroundColor(resumeColor);
                    break;
                case DragEvent.ACTION_DROP:
//                    prompt.append("Item ACTION_DROP: " + "\n");

                    PassObject passObj = (PassObject)event.getLocalState();
                    View view = passObj.view;
                    Exercise passedItem = passObj.item;
                    List<Exercise> srcList = passObj.srcList;
                    AbsListView oldParent = (AbsListView)view.getParent();
                    ItemBaseAdapter srcAdapter = (ItemBaseAdapter)(oldParent.getAdapter());

                    AbsListView newParent = (AbsListView)v.getParent();
                    ItemBaseAdapter destAdapter = (ItemBaseAdapter)(newParent.getAdapter());
                    List<Exercise> destList = destAdapter.getList();

                    int removeLocation = srcList.indexOf(passedItem);
                    int insertLocation = destList.indexOf(me);
				/*
				 * If drag and drop on the same list, same position,
				 * ignore
				 */
                    if(srcList != destList || removeLocation != insertLocation){
                        if(removeItemToList(srcList, passedItem)){
                            destList.add(insertLocation, passedItem);
                        }

                        srcAdapter.notifyDataSetChanged();
                        destAdapter.notifyDataSetChanged();
                    }

                    v.setBackgroundColor(resumeColor);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
//                    prompt.append("Item ACTION_DRAG_ENDED: "  + "\n");
                    v.setBackgroundColor(resumeColor);
                default:
                    break;
            }

            return true;
        }

    }

    AdapterView.OnItemClickListener listOnItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(Edit_day.this,
                    ((Exercise) (parent.getItemAtPosition(position))).getExerciseName(),
                    Toast.LENGTH_SHORT).show();
        }

    };

    private void initItems(){
        db = new DatabaseHandler(getApplicationContext());
//        items1 = new ArrayList<Exercise>();
        items2 = new ArrayList<Exercise>();
        items3 = new ArrayList<Exercise>();
        items3.addAll(db.getAllExercises());

        int bpdayId = blueprintday.getBlueprintDayId();
        List<DayExercise> dayexercises = new ArrayList<DayExercise>();
        dayexercises.addAll(db.getAllDayExercisesByBlueprintDay(bpdayId));

        for(int i=0; i<dayexercises.size(); i++){
            int exid = dayexercises.get(i).getExersieId();
            for (int j=0; j<items3.size();j++){
                if(items3.get(j).getExerciseId()==exid){
                    items2.add(items3.get(j));
                    items3.remove(j);
                }
            }
        }
        db.closeDB();
    }

    private boolean removeItemToList(List<Exercise> l, Exercise it){
        boolean result = l.remove(it);
        return result;
    }

    private boolean addItemToList(List<Exercise> l, Exercise it){
        boolean result = l.add(it);
        return result;
    }
}


//public class Edit_day extends AppCompatActivity {
//    DatabaseHandler db;
//    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
//    List<Rep> reps = new ArrayList<Rep>();
//
//    ExpandableListExerciseAdapter listAdapter;
//    ExpandableListView expListViewExercises,expListViewData;
//    List<Exercise> listDataExercise;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_day);
//        setTitle("Edit day");
//
//        // get the listview
//        expListViewExercises = (ExpandableListView) findViewById(R.id.expandableListViewExercises);
//        expListViewData= (ExpandableListView) findViewById(R.id.expandableListViewDayData);
//
//        // preparing list data
//        prepareListData();
//
//        // Check for ExpandableListAdapter object
//        if (listAdapter == null)
//        {
//            //Create ExpandableListAdapter Object
//            listAdapter = new ExpandableListExerciseAdapter(listDataExercise, this);
//
//            // Set Adapter to ExpandableList Adapter
//            expListViewExercises.setAdapter(listAdapter);
//            expListViewData.setAdapter(listAdapter);
//        }
//        else
//        {
//            // Refresh ExpandableListView data
//            listAdapter.notifyDataSetChanged();
//        }
//
//    }
//
//    /*
//     * Preparing the list data
//     */
//    private void prepareListData() {
//        listDataExercise = new ArrayList<Exercise>();
//        db = new DatabaseHandler(getApplicationContext());
//        exercises.addAll(db.getAllExercises());
//
//        for (int i = 0; i < exercises.size(); i++) {
//            ArrayList<Rep> list = new ArrayList<Rep>();
//
//            reps.addAll(db.getAllRepsByExercise(exercises.get(i).getExerciseId()));
//            for (int j = 0; j < reps.size(); j++){
//                list.add(reps.get(j));
//            }
//            exercises.get(i).setExerciseReps(list);
//            reps.clear();
//        }
//        listDataExercise.addAll(exercises);
//        db.closeDB();
//    }
//}
