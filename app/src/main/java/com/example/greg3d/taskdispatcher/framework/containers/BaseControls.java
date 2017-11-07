package com.example.greg3d.taskdispatcher.framework.containers;

import android.app.Activity;
import android.view.View;

import com.example.greg3d.taskdispatcher.framework.factory.ActivityFactory;

/**
 * Created by greg3d on 05.11.17.
 */

public class BaseControls {

    public <T extends Activity> BaseControls(T view) {
        ActivityFactory.InitActivity(view, this);
        ActivityFactory.setListener((View.OnClickListener)view, this);
    }
}
