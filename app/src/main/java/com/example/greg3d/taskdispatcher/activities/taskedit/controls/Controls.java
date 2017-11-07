package com.example.greg3d.taskdispatcher.activities.taskedit.controls;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.constants.DateFormats;
import com.example.greg3d.taskdispatcher.elements.FormatedDateView;
import com.example.greg3d.taskdispatcher.framework.annotations.DateFormated;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;
import com.example.greg3d.taskdispatcher.framework.containers.BaseControls;

/**
 * Created by greg3d on 21.10.17.
 */

public class Controls extends BaseControls {

    @FindBy(R.id.e_TaskName_Input)
    public EditText name_EditText;

    @FindBy(R.id.e_StartDate_Input)
    @DateFormated(DateFormats.DATE_FORMAT)
    public FormatedDateView startDate_DateText;

    @FindBy(R.id.e_StartTime_Input)
    @DateFormated(DateFormats.TIME_FORMAT)
    public FormatedDateView startTime_DateText;

    @FindBy(R.id.e_EndDate_Input)
    @DateFormated(DateFormats.DATE_FORMAT)
    public FormatedDateView endDate_DateText;

    @FindBy(R.id.e_EndTime_Input)
    @DateFormated(DateFormats.TIME_FORMAT)
    public FormatedDateView endTime_DateText;

    @FindBy(R.id.e_StartDate_Row)
    public TableRow startDate_Row;

    @FindBy(R.id.e_EndDate_Row)
    public TableRow endDate_Row;

    @FindBy(R.id.e_Save_Button)
    public Button save_Button;

    @FindBy(R.id.e_Cancel_Button)
    public Button cancel_Button;

    public <T extends Activity> Controls(T activity) {
        super(activity);
    }
}
