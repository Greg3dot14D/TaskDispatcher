package com.example.greg3d.taskdispatcher.activities.tasklist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.activities.taskedit.TaskEditActivity;
import com.example.greg3d.taskdispatcher.activities.taskhistory.TaskHistoryActivity;
import com.example.greg3d.taskdispatcher.activities.tasklist.adapters.CellAdapter;
import com.example.greg3d.taskdispatcher.activities.tasklist.commands.DeleteTaskCommand;
import com.example.greg3d.taskdispatcher.activities.tasklist.commands.StartCommand;
import com.example.greg3d.taskdispatcher.activities.tasklist.commands.StopCommand;
import com.example.greg3d.taskdispatcher.activities.tasklist.controls.Controls;
import com.example.greg3d.taskdispatcher.constants.State;
import com.example.greg3d.taskdispatcher.dialog.Show;
import com.example.greg3d.taskdispatcher.dialog.YesNoDialog;
import com.example.greg3d.taskdispatcher.framework.factory.ActivityFactory;
import com.example.greg3d.taskdispatcher.framework.factory.ViewFactory;
import com.example.greg3d.taskdispatcher.framework.helpers.ViewHelper;
import com.example.greg3d.taskdispatcher.helpers.ActivitiesManager;
import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.helpers.GridViewHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;

/**
 * Created by greg3d on 28.10.17.
 */

public class TaskListActivity extends Activity implements View.OnClickListener{

    private static final String LOG_TAG = "TaskHistoryActivity";
    GridViewHelper gridView;

    private static TaskListActivity instance;
    public static TaskListActivity getInstance(){
        return instance;
    }
    private View view;
    private Controls controls;
    private View.OnClickListener activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);

        instance = this;
        gridView = new GridViewHelper(this, R.id.gvTaskList)
                .setAdapter(new CellAdapter(this));

        controls = new Controls();
        ActivityFactory.InitActivity(this, controls);
        ActivityFactory.setListener(this, controls);
        ActivityFactory.InitFonts(this, controls);
    }

    public TaskListActivity(){}

    public <T extends View.OnClickListener> TaskListActivity(T activity, View view){
        instance = this;
        this.activity = activity;
        this.view = view;
        gridView = new GridViewHelper(view, R.id.gvTaskList)
                .setAdapter(new CellAdapter(view.getContext()));
        controls = new Controls();
        ViewFactory.InitView(view, controls);
        ActivityFactory.setListener(activity, controls);
        ActivityFactory.InitFonts((Activity)activity, controls);
    }

    @Override
    public void onClick(View view) {
        this.view = view;
        this.onClick(this, view);
    }

    public void onClick(Activity activity, View view){
        ViewHelper v = new ViewHelper(view);

        if(v.idEquals(controls.add_Button)) {
            TaskEditActivity.state = State.ADD;
            ActivitiesManager.startTaskEditActivity(activity);
        }
        if(v.idEquals(controls.edit_Button)) {
            TaskEditActivity.state = State.EDIT;
            ActivitiesManager.startTaskEditActivity(activity);
        }
        else if(v.idEquals(controls.start_Button) && isSelected()){
            if(isStarted())
                new YesNoDialog(activity, new StartCommand(), "Задачка уже в работе !!!\nПовторное нажатие заменит дату начала текущей датой !\nВсе равно начинаем ?").show();
            else
                new YesNoDialog(activity, new StartCommand(), "Начинаем задачку ?").show();
        }
        else if(v.idEquals(controls.stop_Button) && isSelected()){
            if(isStoped())
                new YesNoDialog(activity, new StopCommand(), "Задачка уже остановлена !!!\nПовторное нажатие заменит дату окончания текущей датой !\nВсе равно заканчиваем ?").show();
            else
                new YesNoDialog(activity, new StopCommand(), "Останавливаем задачку ?").show();
        }
        else if(v.idEquals(controls.del_Button) && isSelected())
            new YesNoDialog(activity, new DeleteTaskCommand(), "Удаляем пилюльку ?").show();
    }

    private boolean isStarted(){
        return DBHelper.getRecordById(TaskHistoryModel.class, getSelectedId()).activeState.equals(State.IS_ACTIVE);
    }

    private boolean isStoped(){
        return DBHelper.getRecordById(TaskHistoryModel.class, getSelectedId()).activeState.equals(State.NOT_ACTIVE);
    }

    private boolean isSelected(){
        if(!this.gridView.isSelected()) {
            Show.show(view.getContext(), "Запись не выбрана");
            return false;
        }
        return true;
    }

    // TODO
    public static int getSelectedTaskId(){
        return Integer.valueOf(((TextView)instance.gridView.getView().findViewById(R.id.t_id)).getText().toString());
    }

    public static int getSelectedId(){
        return Integer.valueOf(((TextView)instance.gridView.getView().findViewById(R.id.t_historyId)).getText().toString());
    }

    public static void refresh(){
        instance.gridView.setAdapter(new CellAdapter(instance.view.getContext()));
        TaskHistoryActivity.refresh();
        instance.gridView.setUnSelected();
    }
}
