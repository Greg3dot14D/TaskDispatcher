package com.example.greg3d.taskdispatcher.framework.helpers;

import android.view.View;

import com.example.greg3d.taskdispatcher.elements.BaseElement;

/**
 * Created by greg3d on 30.04.17.
 */
public class ViewHelper {
    private View view;

    public ViewHelper(View view){
        this.view = view;
    }

    public <T extends View> boolean idEquals(T view){
        return this.view.getId() == view.getId();
    }

    public <T extends BaseElement> boolean idEquals(T view){
        return this.view.getId() == view.getView().getId();
    }
}
