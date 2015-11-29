package com.example.mohamed.gymbroplus.Circle;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.gymbroplus.MainActivity;
import com.example.mohamed.gymbroplus.Manage_Exercises;


public class MyCircleLayoutItem extends CircularLayoutItem {


    int indexClicknumber;
    int indexFocusnumber;
    //MainActivity ma = new MainActivity();
//    TextView menuText = MainActivity.menuText;
//Context context;
    //TextView menuText1 = ma.setmenuText();
    int childcount=4;

    public MyCircleLayoutItem(Context context) {
        super(context);initialize(context);

    }

    public MyCircleLayoutItem(Context context, CircleLayout cl) {
        super(context, cl);
        initialize(context);
    }

    public MyCircleLayoutItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public MyCircleLayoutItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }


    public void initialize(Context c)
    {
        final Context context=c;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                menuitemOnClick(getIndex(),context);
                //Toast.makeText(getContext(),"Item number: "+itemnumber,Toast.LENGTH_SHORT).show();
            }
        });

        this.setOnFocusListener(new OnFocusListener() {
            @Override
            public void onFocus() {
                menuitemOnFocus(getIndex(), context);
            }

            @Override
            public void onUnFocus() {

            }
        });
    }

    public void menuitemOnClick(int index,Context content){
        //TextView viewTextRep = (TextView) addView.findViewById(R.id.textViewRep);
        //childcount = 4;
        int itemnumber=mod(index);
        //Toast.makeText(getContext(),"Item number: "+itemnumber+ " is now on focus ",Toast.LENGTH_SHORT).show();
        switch (itemnumber) {
            case 0:
                // they are executed if variable == c1
                break;
            case 1:
                // they are executed if variable == c2
                break;
            case 2:
                // they are executed if variable == c2
//                Intent startActivity = new Intent();
//                startActivity.setClass(content, Manage_Exercises.class);
//                startActivity.setAction(Manage_Exercises.class.getName());
//                startActivity.setFlags(
//                        Intent.FLAG_ACTIVITY_NEW_TASK
//                                | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                content.startActivity(startActivity);
                Intent intent = new Intent(content, Manage_Exercises.class);
                content.startActivity(intent);
                break;
            case 3:
                // they are executed if variable ==  any of the above c's
                break;
        }
    }
    public void menuitemOnFocus(int index,Context content){
        //TextView viewTextRep = (TextView) addView.findViewById(R.id.textViewRep);
        //childcount = 4;
        int itemnumber=mod(index);
        //Toast.makeText(getContext(),"Item number: "+itemnumber+ " is now on focus ",Toast.LENGTH_SHORT).show();
        switch (itemnumber) {
            case 0:
                // they are executed if variable == c1
//                menuText.setText("Start Exercise");
                Toast.makeText(getContext(),"Item number: "+itemnumber,Toast.LENGTH_SHORT).show();
                break;
            case 1:
                // they are executed if variable == c2
//                menuText.setText("Make Blueprint");
                Toast.makeText(getContext(),"Item number: "+itemnumber,Toast.LENGTH_SHORT).show();
                break;
            case 2:
                // they are executed if variable == c2
//                menuText.setText("Manage Exercise");
                Toast.makeText(getContext(),"Item number: "+itemnumber, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                // they are executed if variable ==  any of the above c's
//                menuText.setText("Gym Bro");
                Toast.makeText(getContext(),"Item number: "+itemnumber,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //modulo
    private int mod(int x)
    {
        int result = x % childcount;
        return result < 0? result + childcount : result;
    }
}
