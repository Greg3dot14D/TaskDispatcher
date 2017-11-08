package com.example.greg3d.taskdispatcher.activities.taskhistory.controls;

import android.widget.Button;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.constants.DateFormats;
import com.example.greg3d.taskdispatcher.elements.FormatedDateView;
import com.example.greg3d.taskdispatcher.framework.annotations.DateFormated;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;


/**
 * Created by greg3d on 21.10.17.
 */

public class Controls {
    @FindBy(R.id.h_del_button)
    public Button del_Button;

    @FindBy(R.id.h_edit_button)
    public Button edit_Button;

    @FindBy(R.id.h_Filter)
    @DateFormated(DateFormats.MONTH_YEAR_FORMAT)
    public FormatedDateView filter_DateView;
}
