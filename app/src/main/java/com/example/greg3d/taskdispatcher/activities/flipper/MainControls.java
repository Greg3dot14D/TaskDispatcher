package com.example.greg3d.taskdispatcher.activities.flipper;

import android.support.design.widget.FloatingActionButton;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;

/**
 * Created by greg3d on 10.11.17.
 */

public class MainControls {
    @FindBy(R.id.fab_hide)
    public FloatingActionButton hide_Fab;

    @FindBy(R.id.fab_add)
    public FloatingActionButton add_Fab;

    @FindBy(R.id.fab_edit)
    public FloatingActionButton edit_Fab;

    @FindBy(R.id.fab_delete)
    public FloatingActionButton delete_Fab;
}
