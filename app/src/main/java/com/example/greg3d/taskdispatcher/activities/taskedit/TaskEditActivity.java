package com.example.greg3d.taskdispatcher.activities.taskedit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.activities.taskedit.commands.EditDateFieldCommand;
import com.example.greg3d.taskdispatcher.activities.taskedit.controls.Controls;
import com.example.greg3d.taskdispatcher.activities.taskhistory.TaskHistoryActivity;
import com.example.greg3d.taskdispatcher.activities.tasklist.TaskListActivity;
import com.example.greg3d.taskdispatcher.constants.State;
import com.example.greg3d.taskdispatcher.controller.DBController;
import com.example.greg3d.taskdispatcher.dialog.DatePickerDialogImpl;
import com.example.greg3d.taskdispatcher.dialog.MessageDialog;
import com.example.greg3d.taskdispatcher.dialog.TimePickerDialogImpl;
import com.example.greg3d.taskdispatcher.framework.helpers.ViewHelper;
import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.helpers.Tools;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;
import com.example.greg3d.taskdispatcher.model.TaskModel;

import java.util.Date;

/**
 * Created by greg3d on 28.10.17.
 */

public class TaskEditActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG = "TaskEditActivity";

    private static TaskEditActivity instance;
    public static TaskEditActivity getInstance(){
        return instance;
    }

    private Controls controls;
    public static int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        instance = this;
        controls = new Controls(this);
        //ActivityFactory.InitFonts(this, controls.save_Button, CssManager.getEditButtonCss());
        //ActivityFactory.InitFonts(this, controls.cancel_Button, CssManager.getEditButtonCss());
        this.updateFieldsBySelectedRecord();
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
            else if(state == State.EDIT){
                TaskModel model = new TaskModel();
                Date lastDate = new Date();
                model.name = controls.name_EditText.getText().toString();
                model.lastDate = lastDate;
                model.id = TaskListActivity.getSelectedObject().taskId;
                DBHelper.getInstance().editRecord(model);

                TaskHistoryModel history = TaskListActivity.getSelectedObject();
                history.name = model.name;
                history.lastDate = lastDate;
                DBHelper.getInstance().updateRecord(history);
            }
            else if(state == State.EDIT_HISTORY){
                Date startDate = Tools.glueDateTime(controls.startDate_DateText.getDate(), controls.startTime_DateText.getDate());
                Date endDate = Tools.glueDateTime(controls.endDate_DateText.getDate(), controls.endTime_DateText.getDate());
                if(startDate.getTime() > endDate.getTime()){
                    new MessageDialog(this, "АХТУНГ !!!","Че та, кажется - Время начала БОЛЬШЕ Времени завершения !!!");
                    return;
                }
                TaskHistoryModel history = TaskHistoryActivity.getSelectedObject();
                history.name = controls.name_EditText.getText().toString();
                history.startDate = startDate;
                history.endDate = endDate;
                DBHelper.getInstance().editRecord(history);

                TaskModel model = new TaskModel();
                model.id = history.taskId;
                DBController.updateLastDateForTask(model);
            }
            TaskListActivity.refresh();
            this.finish();
        }
        else if(v.idEquals(controls.startDate_DateText)){
            new DatePickerDialogImpl(this, controls.startDate_DateText.getDate(), new EditDateFieldCommand(controls.startDate_DateText)).show();
        }
        else if(v.idEquals(controls.endDate_DateText)){
            new DatePickerDialogImpl(this, controls.startDate_DateText.getDate(), new EditDateFieldCommand(controls.endDate_DateText)).show();
        }
        else if(v.idEquals(controls.startTime_DateText)){
            new TimePickerDialogImpl(this, controls.startTime_DateText.getDate(), new EditDateFieldCommand(controls.startTime_DateText)).show();
        }
        else if(v.idEquals(controls.endTime_DateText)){
            new TimePickerDialogImpl(this, controls.endTime_DateText.getDate(), new EditDateFieldCommand(controls.endTime_DateText)).show();        }
    }

    // Заполняем поля на форме по значениям выбранной записи
    private void updateFieldsBySelectedRecord(){
        if(state == State.ADD){
            controls.startDate_Row.setEnabled(false);
            controls.endDate_Row.setEnabled(false);
        }
        else if(state == State.EDIT){
            try {
                controls.name_EditText.setText(TaskListActivity.getSelectedObject().name);
            }catch(NullPointerException e){}
        }
        else if(state == State.EDIT_HISTORY){
            try {
                controls.startDate_Label.setVisibility(View.VISIBLE);
                controls.startDate_DateText.getView().setVisibility(View.VISIBLE);
                controls.startTime_DateText.getView().setVisibility(View.VISIBLE);
                controls.endDate_Label.setVisibility(View.VISIBLE);
                controls.endDate_DateText.getView().setVisibility(View.VISIBLE);
                controls.endTime_DateText.getView().setVisibility(View.VISIBLE);
                TaskHistoryModel task = TaskHistoryActivity.getSelectedObject();
                controls.name_EditText.setText(task.name);
                controls.startDate_DateText.setDate(task.startDate);
                controls.startTime_DateText.setDate(task.startDate);
                controls.endDate_DateText.setDate(task.endDate);
                controls.endTime_DateText.setDate(task.endDate);

            }catch(NullPointerException e){}
        }
    }
}
