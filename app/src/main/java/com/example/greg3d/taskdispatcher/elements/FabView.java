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

    public FloatingActionButton getWrappedElement(){ return (FloatingActionButton)this.view;}

    public void show(){
        this.getWrappedElement().setClickable(true);
        if(this.showAnimation != null)
            this.getWrappedElement().startAnimation(this.showAnimation);
        this.getWrappedElement().show();
    }

    public void hide(){
        this.flip();
        this.getWrappedElement().setClickable(false);
    }

    public void flip(){
        if(this.hideAnimation != null)
            this.getWrappedElement().startAnimation(this.hideAnimation);
    }

    public Animation getHideAnimation(){
        return this.hideAnimation;
    }

    public Animation getShowAnimation(){
        return this.showAnimation;
    }

    public void setAnimationStartOffset(long offset){
        if(this.showAnimation != null)
            this.showAnimation.setStartOffset(offset);
        if(this.hideAnimation != null)
            this.hideAnimation.setStartOffset(offset);
    }
}
