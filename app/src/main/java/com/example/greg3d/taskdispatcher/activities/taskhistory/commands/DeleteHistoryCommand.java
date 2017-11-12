package com.example.greg3d.taskdispatcher.activities.taskhistory.commands;


import com.example.greg3d.taskdispatcher.activities.taskhistory.TaskHistoryActivity;
import com.example.greg3d.taskdispatcher.activities.tasklist.TaskListActivity;
import com.example.greg3d.taskdispatcher.commands.ICommand;
import com.example.greg3d.taskdispatcher.controller.DBController;
import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;
import com.example.greg3d.taskdispatcher.model.TaskModel;

/**
 * Created by greg3d on 06.11.17.
 */

public class DeleteHistoryCommand implements ICommand {
    @Override
    public void execute() {
        // TODO

        TaskHistoryModel model = new TaskHistoryModel();
        int taskId = TaskHistoryActivity.getSelectedObject().taskId;
        model.id = TaskHistoryActivity.getSelectedObject().id;
        DBHelper.getInstance().deleteRecord(model);

        TaskModel task = new TaskModel();
        task.id = taskId;
        DBController.updateLastDateForTask(task);

        TaskListActivity.refresh();
    }
}
