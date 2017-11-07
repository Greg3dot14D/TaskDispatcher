package com.example.greg3d.taskdispatcher.helpers;

import android.view.View;

import com.example.greg3d.taskdispatcher.R;

/**
 * Created by greg3d on 28.10.17.
 */

public class CellHelper {
    private View view;
    private long id;

    public long getId(){
        return this.id;
    }

    public void resetSelect(){
        if(this.view == null)
            return;
        this.view.setBackgroundResource(R.drawable.side_default_cell);
    }

    public void setSelect(View view, long id){
        this.id = id;
        this.view = view;
        this.view.setBackgroundResource(R.drawable.side_selected_cell);
        this.view.setSelected(true);
    }

    public View getView(){
        return this.view;
    }
}
