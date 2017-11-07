package com.example.greg3d.taskdispatcher.activities.tasklist.commands;


import com.example.greg3d.taskdispatcher.activities.tasklist.TaskListActivity;
import com.example.greg3d.taskdispatcher.commands.ICommand;
import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.model.TaskModel;

/**
 * Created by greg3d on 06.11.17.
 */

public class DeleteTaskCommand implements ICommand {
    @Override
    public void execute() {
        // TODO
        TaskModel model = new TaskModel();
        model.id = TaskListActivity.getSelectedTaskId();
        DBHelper.getInstance().deleteRecord(model);
        TaskListActivity.refresh();
    }
}
