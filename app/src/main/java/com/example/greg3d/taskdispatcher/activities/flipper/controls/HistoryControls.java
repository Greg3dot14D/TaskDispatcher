package com.example.greg3d.taskdispatcher.activities.flipper.controls;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.elements.FabView;
import com.example.greg3d.taskdispatcher.framework.annotations.Animation;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;

/**
 * Created by greg3d on 10.11.17.
 */

public class HistoryControls {

    public boolean isShown = true;

    @Animation(show = R.anim.fab_show_200, hide = R.anim.fab_hide_200)
    @FindBy(R.id.fab_history_hide)
    public FabView hide_Fab;

    @Animation(show = R.anim.fab_show_100, hide = R.anim.fab_hide_100)
    @FindBy(R.id.fab_history_edit)
    public FabView edit_Fab;

    @Animation(show = R.anim.fab_show, hide = R.anim.fab_hide)
    @FindBy(R.id.fab_history_delete)
    public FabView delete_Fab;

    public void hideControls(){
        this.hide_Fab.hide();
        this.edit_Fab.hide();
        this.delete_Fab.hide();
    }

    public void showControls(){
        this.hide_Fab.show();
        this.edit_Fab.show();
        this.delete_Fab.show();
    }

}
