package com.example.greg3d.taskdispatcher.fakes;

import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;
import com.example.greg3d.taskdispatcher.model.TaskModel;

import java.util.Date;

/**
 * Created by greg3d on 06.11.17.
 */

public class FakeData {

    private DBHelper db;
    public FakeData(DBHelper dbHelper){
        this.db = dbHelper;
    }

    public void createFakes(){
        Date lastDate = new Date();
        TaskModel task = new TaskModel();
        TaskHistoryModel history = new TaskHistoryModel();

        db.dropTable(task);
        db.createTable(task);

        db.dropTable(history);
        db.createTable(history);

        task.lastDate = lastDate;
        task.name = "Новая задача";

        db.insertRecord(task);

        history.taskId = 1;
        history.name = task.name;
        history.startDate = new Date();
        history.endDate = new Date();
        history.lastDate = lastDate;

        db.insertRecord(history);
    }
}
