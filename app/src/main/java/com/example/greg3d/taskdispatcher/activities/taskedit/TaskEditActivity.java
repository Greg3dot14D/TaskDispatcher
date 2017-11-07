package com.example.greg3d.taskdispatcher.activities.taskedit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.activities.taskedit.controls.Controls;
import com.example.greg3d.taskdispatcher.activities.tasklist.TaskListActivity;
import com.example.greg3d.taskdispatcher.constants.State;
import com.example.greg3d.taskdispatcher.framework.helpers.ViewHelper;
import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;
import com.example.greg3d.taskdispatcher.model.TaskModel;

import java.util.Date;

/**
 * Created by greg3d on 28.10.17.
 */

public class TaskEditActivity extends Activity implements View.OnClickListener{

    private static final String LOG = "TaskEditActivity";

    private static TaskEditActivity instance;
    public static TaskEditActivity getInstance(){
        return instance;
    }

    private Controls controls;
    public static Controls getControls(){return instance.controls;}

    private static TaskHistoryModel model;
    public static int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        instance = this;
        controls = new Controls(this);
        this.updateFieldsBySelectedRecord();
    }

    public static void setModel(TaskHistoryModel inModel){
        model = inModel;
    }

//    // Вызывается перед тем, как Активность перестает быть "видимой".
//    @Override
//    public void onStop(){
//        super.onStop();
//        model = null;
//    }

    @Override
    public void onClick(View view) {
        ViewHelper v = new ViewHelper(view);

        if(v.idEquals(controls.cancel_Button))
            this.finish();
        else if(v.idEquals(controls.save_Button)){
            if(state == State.ADD) {
                TaskModel model = new TaskModel();
                Date lastDate = new Date();
                model.name = controls.name_EditText.getText().toString();
                model.lastDate = lastDate;
                DBHelper.getInstance().insertRecord(model);

                model = DBHelper.getInstance().getLastRecord(model);

                TaskHistoryModel history = new TaskHistoryModel();
                history.name = model.name;
                history.lastDate = lastDate;
                history.startDate = lastDate;
                history.endDate = lastDate;
                history.taskId = model.id;
                DBHelper.getInstance().insertRecord(history);
            }
            else{
                TaskModel model = new TaskModel();
                Date lastDate = new Date();
                model.name = controls.name_EditText.getText().toString();
                model.lastDate = lastDate;
                model.id = TaskListActivity.getSelectedTaskId();
                DBHelper.getInstance().editRecord(model);

                TaskHistoryModel history = new TaskHistoryModel();
                history.name = model.name;
                history.lastDate = lastDate;
                history.taskId = model.id;
                history.id = TaskListActivity.getSelectedId();
                DBHelper.getInstance().editRecord(history);
            }
            TaskListActivity.refresh();
            this.finish();
        }
    }

    // Заполняем поля на форме по значениям выбранной записи
    private void updateFieldsBySelectedRecord(){
        if(state == State.ADD){
            controls.startDate_Row.setEnabled(false);
            controls.endDate_Row.setEnabled(false);
        }
        else if(state == State.EDIT){
            TaskModel task = new TaskModel();
            task = DBHelper.getRecordById(task,TaskListActivity.getSelectedTaskId());
            controls.name_EditText.setText(task.name);
        }
    }

    private void updateRecord(TaskHistoryModel model){
        DBHelper db = DBHelper.getInstance();

//        FarmacyModel fModel = new FarmacyModel();
//        fModel.volume = model.volume;
//        fModel.name = model.name;
//        fModel.lastDate = model.purchaseDate;
//
//        if(this.model != null){
//            model.farmacyId = this.model.farmacyId;
//            fModel.id = this.model.farmacyId;
//        }
//
//        switch(state){
//            case State.BUY:
//                db.updateRecord(fModel);
//                db.insertRecord(model);
//                break;
//            case State.ADD:
//                db.insertRecord(fModel);
//                fModel = db.getLastRecord(fModel);
//                model.farmacyId = fModel.id;
//                db.insertRecord(model);
//                break;
//            case State.EDIT:
//                fModel.id = this.model.farmacyId;
//                db.updateRecord(fModel);
//
//                model.id = this.model.id;
//                model.farmacyId = this.model.farmacyId;
//                db.updateRecord(model);
//                break;
//        }
    }
}
