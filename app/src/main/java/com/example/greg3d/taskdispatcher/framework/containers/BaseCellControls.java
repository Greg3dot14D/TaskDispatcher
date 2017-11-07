package com.example.greg3d.taskdispatcher.framework.containers;

import android.app.Activity;

import com.example.greg3d.taskdispatcher.framework.factory.ActivityFactory;

/**
 * Created by greg3d on 05.11.17.
 */

public class BaseCellControls {

    public <T> BaseCellControls(T view) {
        ActivityFactory.InitActivity((Activity)view, this);
    }
}
