package com.example.greg3d.taskdispatcher.framework.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by greg3d on 29.10.17.
 */

public class BaseSpinnerStringAdapter extends ArrayAdapter<String> {

    public BaseSpinnerStringAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_item, getData());
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    protected static List<String> getData(){return null;}
}
