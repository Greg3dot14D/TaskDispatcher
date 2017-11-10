package com.example.greg3d.taskdispatcher.activities.tasklist.controls;

import android.support.design.widget.FloatingActionButton;
import android.widget.Button;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.css.EditButtonCss;
import com.example.greg3d.taskdispatcher.framework.annotations.CSS;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;

/**
 * Created by greg3d on 21.10.17.
 */

public class Controls {

    @CSS(EditButtonCss.class)
    @FindBy(R.id.tl_add_button)
    public Button add_Button;

    @CSS(EditButtonCss.class)
    @FindBy(R.id.tl_del_button)
    public Button del_Button;

    @CSS(EditButtonCss.class)
    @FindBy(R.id.tl_edit_button)
    public Button edit_Button;

    @CSS(EditButtonCss.class)
    @FindBy(R.id.tl_start_button)
    public Button start_Button;

    @CSS(EditButtonCss.class)
    @FindBy(R.id.tl_stop_button)
    public Button stop_Button;



}
