package com.example.greg3d.taskdispatcher.activities.flipper.controls;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.elements.FabView;
import com.example.greg3d.taskdispatcher.framework.annotations.Animation;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;

/**
 * Created by greg3d on 10.11.17.
 */

public class MainControls {
//    @FindBy(R.id.fab_hide)
//    public FloatingActionButton hide_Fab;

    @Animation(show = R.anim.fab_show_300, hide = R.anim.fab_hide_300)
    @FindBy(R.id.fab_hide)
    public FabView hide_Fab;

    @Animation(show = R.anim.fab_show_200, hide = R.anim.fab_hide_200)
    @FindBy(R.id.fab_add)
    public FabView add_Fab;

    @Animation(show = R.anim.fab_show_100, hide = R.anim.fab_hide_100)
    @FindBy(R.id.fab_edit)
    public FabView edit_Fab;

    @Animation(show = R.anim.fab_show, hide = R.anim.fab_hide)
    @FindBy(R.id.fab_delete)
    public FabView delete_Fab;
}
