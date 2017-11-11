package com.example.greg3d.taskdispatcher.elements;

import android.support.design.widget.FloatingActionButton;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.greg3d.taskdispatcher.framework.annotations.SetView;
import com.example.greg3d.taskdispatcher.framework.interfaces.ISetAnimation;

/**
 * Created by greg3d on 05.11.17.
 */
@SetView(FloatingActionButton.class)
public class FabView<T extends TextView> extends BaseElement implements ISetAnimation{

    private Animation hideAnimation;
    private Animation showAnimation;

    public FabView(Object view) {
        super(view);
    }

    @Override
    public void setHideAnimation(Animation hideAnimation){
        this.hideAnimation = hideAnimation;
    }

    @Override
    public void setShowAnimation(Animation showAnimation){
        this.showAnimation = showAnimation;
    }

    private FloatingActionButton getWrappedElement(){ return (FloatingActionButton)this.view;}

    public void show(){
        this.getWrappedElement().setClickable(true);
        if(this.showAnimation != null)
            this.getWrappedElement().startAnimation(this.showAnimation);
        this.getWrappedElement().show();
    }

    public void hide(){
        this.getWrappedElement().setClickable(false);
        if(this.hideAnimation != null)
            this.getWrappedElement().startAnimation(this.hideAnimation);
    }
}
