package com.example.greg3d.taskdispatcher.timer;

import com.example.greg3d.taskdispatcher.activities.flipper.MainActivity;
import com.example.greg3d.taskdispatcher.elements.FormatedDateView;
import com.example.greg3d.taskdispatcher.helpers.Tools;

import java.util.Date;
import java.util.TimerTask;

/**
 * Created by greg3d on 11.06.17.
 */
public class SpentTimerTask extends TimerTask {

    private FormatedDateView daterTimeView;
    private Date startDate;

    public SpentTimerTask(FormatedDateView daterTimeView, Date startDate){
        this.daterTimeView = daterTimeView;
        this.startDate = startDate;
    }

    @Override
    public void run() {
        MainActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                daterTimeView.setDate(Tools.getDifTime(startDate, new Date()));
            }
        });

    }
}
