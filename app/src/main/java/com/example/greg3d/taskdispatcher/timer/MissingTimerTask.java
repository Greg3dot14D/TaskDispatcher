package com.example.greg3d.taskdispatcher.timer;

import com.example.greg3d.taskdispatcher.activities.flipper.MainActivity;
import com.example.greg3d.taskdispatcher.activities.tasklist.TaskListActivity;

import java.util.TimerTask;

/**
 * Created by greg3d on 11.06.17.
 */
public class MissingTimerTask extends TimerTask {
    @Override
    public void run() {
        MainActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TaskListActivity.getInstance().refreshDif();
            }
        });
    }
}
