package com.example.greg3d.taskdispatcher.activities.taskhistory.commands;


import com.example.greg3d.taskdispatcher.activities.taskhistory.TaskHistoryActivity;
import com.example.greg3d.taskdispatcher.activities.tasklist.TaskListActivity;
import com.example.greg3d.taskdispatcher.commands.IDateCommand;

import java.util.Calendar;

/**
 * Created by greg3d on 06.11.17.
 */

public class FilterByMonthCommand implements IDateCommand {
    @Override
    public void execute(Calendar calendar) {
        TaskHistoryActivity.setFilter(calendar.getTime());
        TaskListActivity.refresh();
    }
}
