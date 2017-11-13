package com.example.greg3d.taskdispatcher.activities.tasklist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.activities.flipper.MainActivity;
import com.example.greg3d.taskdispatcher.activities.taskhistory.TaskHistoryActivity;
import com.example.greg3d.taskdispatcher.activities.tasklist.adapters.CellAdapter;
import com.example.greg3d.taskdispatcher.activities.tasklist.commands.StartCommand;
import com.example.greg3d.taskdispatcher.activities.tasklist.commands.StopCommand;
import com.example.greg3d.taskdispatcher.activities.tasklist.controls.Controls;
import com.example.greg3d.taskdispatcher.constants.State;
import com.example.greg3d.taskdispatcher.dialog.Show;
import com.example.greg3d.taskdispatcher.dialog.YesNoDialog;
import com.example.greg3d.taskdispatcher.framework.factory.ActivityFactory;
import com.example.greg3d.taskdispatcher.framework.factory.ViewFactory;
import com.example.greg3d.taskdispatcher.framework.helpers.ViewHelper;
import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.helpers.GridViewHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;

/**
 * Created by greg3d on 28.10.17.
 */

public class TaskListActivity extends Activity implements View.OnClickListener{

    private static final String LOG_TAG = "TaskHistoryActivity";
    private GridViewHelper gridView;

    private static TaskListActivity instance;
    public static TaskListActivity getInstance(){
        return instance;
    }
    private View view;
    private Controls controls;

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
        this.view = view;
        gridView = new GridViewHelper(view, R.id.gvTaskList)
                .setAdapter(new CellAdapter(view.getContext()));

        gridView.getGridView()
                .setOnItemLongClickListener(new GridViewListener(gridView));

        controls = new Controls();
        ViewFactory.InitView(view, controls);
        ActivityFactory.setListener(activity, controls);
        ActivityFactory.InitFonts((Activity)activity, controls);
    }

    private static class GridViewListener implements AdapterView.OnItemLongClickListener{
        private GridViewHelper gridView;

        public GridViewListener(GridViewHelper gridView){
            this.gridView = gridView;
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            //this.activity.startActionMode(new ActionBarCallBack(this.activity));
            //TimerManager.stopTimer();
            MainActivity.showFabs();
            gridView.getCellHelper().resetSelect();
            gridView.getCellHelper().setSelect(view, l);
            gridView.setSelected(i);
            //TimerManager.addTimer();
            return false;
        }
    }

//    public static class ActionBarCallBack implements ActionMode.Callback {
//
//        private Activity activity;
//
//        public ActionBarCallBack( Activity activity){
//            this.activity = activity;
//        }
//
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            // TODO Auto-generated method stub
//            if("Edit".equals(item.getTitle())){
//                TaskEditActivity.state = State.EDIT;
//                ActivitiesManager.startTaskEditActivity(activity);
//            }
//            else if("Add".equals(item.getTitle())){
//                TaskEditActivity.state = State.ADD;
//                ActivitiesManager.startTaskEditActivity(activity);
//            }
//            else if("Delete".equals(item.getTitle())){
//                new YesNoDialog(activity, new DeleteTaskCommand(), "Удаляем пилюльку ?").show();
//            }
//            //mode.finish();
//            return false;
//        }
//
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            // TODO Auto-generated method stub
//            mode.getMenuInflater().inflate(R.menu.cab, menu);
//            return true;
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            // TODO Auto-generated method stub
//
//            //mode.setTitle("CheckBox is Checked");
//            return false;
//        }
//    }

    @Override
    public void onClick(View view) {
        this.view = view;
        this.onClick(this, view);
    }

    public void onClick(Activity activity, View view){
        ViewHelper v = new ViewHelper(view);

        if(v.idEquals(controls.start_Button) && isSelected()){
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
//        else if(v.idEquals(controls.title_TextView))
//            MainActivity.showFabs();
    }

    private boolean isStarted(){
        return DBHelper.getRecordById(TaskHistoryModel.class, getSelectedObject().id).activeState.equals(State.IS_ACTIVE);
    }

    private boolean isStoped(){
        return DBHelper.getRecordById(TaskHistoryModel.class, getSelectedObject().id).activeState.equals(State.NOT_ACTIVE);
    }

    public boolean isSelected(){
        if(!this.gridView.isSelected()) {
            Show.show(view.getContext(), "Запись не выбрана");
            return false;
        }
        return true;
    }

    public static TaskHistoryModel getSelectedObject(){
        return (TaskHistoryModel)instance.gridView.getSelectedObject();
    }

    public void refreshDif(){
        this.gridView.setAdapter(new CellAdapter(view.getContext(), this.gridView));
    }

    public static void refresh(){
        instance.gridView.setAdapter(new CellAdapter(instance.view.getContext()));
        TaskHistoryActivity.refresh();
        instance.gridView.setUnSelected();
    }
}
