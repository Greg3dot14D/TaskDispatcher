package com.example.greg3d.taskdispatcher.activities.tasklist.controls;

import android.widget.TextView;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.constants.DateFormats;
import com.example.greg3d.taskdispatcher.css.NumericCss;
import com.example.greg3d.taskdispatcher.elements.FormatedDateView;
import com.example.greg3d.taskdispatcher.framework.annotations.CSS;
import com.example.greg3d.taskdispatcher.framework.annotations.DateFormated;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;

/**
 * Created by greg3d on 21.10.17.
 */

public class TaskHistoryView {
        @FindBy(R.id.t_Name)
        public TextView name_TextView;

        @FindBy(R.id.t_Status)
        public TextView status_TextView;

        @CSS(NumericCss.class)
        @FindBy(R.id.t_StartDate)
        @DateFormated(DateFormats.DATE_TIME_FORMAT)
        public FormatedDateView startDate_DateView;

        @CSS(NumericCss.class)
        @FindBy(R.id.t_EndDate)
        @DateFormated(DateFormats.DATE_TIME_FORMAT)
        public FormatedDateView endDate_DateView;

        @CSS(NumericCss.class)
        @FindBy(R.id.t_Duration)
        @DateFormated(DateFormats.TIME_FORMAT)
        public FormatedDateView duration_DateView;

        @CSS(NumericCss.class)
        @FindBy(R.id.t_Duration)
        public TextView duration_TextView;
}
