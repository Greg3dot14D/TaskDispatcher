package com.example.greg3d.taskdispatcher.elements;

import android.widget.TextView;

import com.example.greg3d.taskdispatcher.framework.annotations.SetView;


/**
 * Created by greg3d on 05.11.17.
 */
@SetView(TextView.class)
public class IntegerView <T extends TextView> extends BaseElement{

    public IntegerView(Object view) {
        super(view);
    }

    public void setValue(Object value){
        ((T)this.view).setText(value.toString());
    }

    public Integer getValue(){
        return Integer.valueOf(((T)this.view).getText().toString());
    }
}
