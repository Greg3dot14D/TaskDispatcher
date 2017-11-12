package com.example.greg3d.taskdispatcher.activities.tasklist.commands;

import com.example.greg3d.taskdispatcher.activities.tasklist.TaskListActivity;
import com.example.greg3d.taskdispatcher.commands.ICommand;
import com.example.greg3d.taskdispatcher.constants.State;
import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;
import com.example.greg3d.taskdispatcher.model.TaskModel;

import java.util.Date;

/**
 * Created by greg3d on 08.11.17.
 */

public class StartCommand implements ICommand{
    @Override
    public void execute() {
        Date lastDate = new Date();

        TaskModel task = new TaskModel();
        task = DBHelper.getRecordById(task, TaskListActivity.getSelectedObject().taskId);
        task.lastDate = lastDate;

        TaskHistoryModel history = new TaskHistoryModel();
        history.taskId = task.id;
        history.name = task.name;
        history.lastDate = lastDate;
        history.startDate = lastDate;
        history.endDate = lastDate;
        history.activeState = State.IS_ACTIVE;

        DBHelper.getInstance().editRecord(task);
        DBHelper.getInstance().insertRecord(history);
        TaskListActivity.refresh();
    }
}
