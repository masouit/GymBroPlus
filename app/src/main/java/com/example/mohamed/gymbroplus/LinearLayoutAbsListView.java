package com.example.mohamed.gymbroplus;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.LinearLayout;

public class LinearLayoutAbsListView extends LinearLayout {
	
	AbsListView absListView;

	public LinearLayoutAbsListView(Context context) {
		super(context);
	}

	public LinearLayoutAbsListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LinearLayoutAbsListView(Context context, AttributeSet attrs,
								   int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setAbsListView(AbsListView alv){
		absListView = alv;
	}

}
