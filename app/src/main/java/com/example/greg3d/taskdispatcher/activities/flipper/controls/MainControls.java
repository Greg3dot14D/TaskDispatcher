package com.example.greg3d.taskdispatcher.activities.flipper.controls;

import android.view.View;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.elements.FabView;
import com.example.greg3d.taskdispatcher.framework.annotations.Animation;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;

/**
 * Created by greg3d on 10.11.17.
 */

public class MainControls {

    public boolean isShown = true;

    //@Animation(show = R.anim.fab_show, hide = R.anim.fab_hide)
    @FindBy(R.id.fab_hide)
    public FabView hide_Fab;

    @Animation(show = R.anim.fab_show_300, hide = R.anim.fab_hide_300)
    @FindBy(R.id.fab_add)
    public FabView add_Fab;

    @Animation(show = R.anim.fab_show_200, hide = R.anim.fab_hide_200)
    @FindBy(R.id.fab_edit)
    public FabView edit_Fab;

    @Animation(show = R.anim.fab_show_100, hide = R.anim.fab_hide_100)
    @FindBy(R.id.fab_delete)
    public FabView delete_Fab;

    public void closeControls(){
        if(isShown)
            this.hideControls();
        this.hide_Fab.getWrappedElement().setVisibility(View.INVISIBLE);
    }

    public void openControls(){
        if(isShown)
            this.showControls();
        this.hide_Fab.getWrappedElement().setVisibility(View.VISIBLE);
    }

    public void hideControls(){
        this.hide_Fab.getWrappedElement().setAlpha(new Float(0.5));
        //this.hide_Fab.hide();
        this.add_Fab.hide();
        this.edit_Fab.hide();
        this.delete_Fab.hide();
    }

    public void showControls(){
        this.hide_Fab.getWrappedElement().setAlpha(new Float(1));
        //this.hide_Fab.show();
        this.add_Fab.show();
        this.edit_Fab.show();
        this.delete_Fab.show();
    }
    public void setAnimationStartOffset(long offset){
        this.hide_Fab.setAnimationStartOffset(offset);
        this.add_Fab.setAnimationStartOffset(offset);
        this.edit_Fab.setAnimationStartOffset(offset);
        this.delete_Fab.setAnimationStartOffset(offset);
    }
}
