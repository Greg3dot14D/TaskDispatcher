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

public class StopCommand implements ICommand{

    @Override
    public void execute() {
        Date lastDate = new Date();

        TaskModel task = new TaskModel();
        task = DBHelper.getInstance().getRecordById(task, TaskListActivity.getSelectedTaskId());
        task.lastDate = lastDate;

        TaskHistoryModel history = new TaskHistoryModel();
        history.id = TaskListActivity.getSelectedId();
        history.lastDate = lastDate;
        history.endDate = lastDate;
        history.activeState = State.NOT_ACTIVE;
        DBHelper.getInstance().editRecord(task);
        DBHelper.getInstance().editRecord(history);
        TaskListActivity.refresh();
    }
}
