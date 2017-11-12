package com.example.greg3d.taskdispatcher.activities.taskhistory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.activities.flipper.MainActivity;
import com.example.greg3d.taskdispatcher.activities.taskhistory.adapters.CellAdapter;
import com.example.greg3d.taskdispatcher.activities.taskhistory.commands.FilterByMonthCommand;
import com.example.greg3d.taskdispatcher.activities.taskhistory.controls.Controls;
import com.example.greg3d.taskdispatcher.controller.DBController;
import com.example.greg3d.taskdispatcher.dialog.DatePickerDialogImpl;
import com.example.greg3d.taskdispatcher.dialog.Show;
import com.example.greg3d.taskdispatcher.framework.factory.ActivityFactory;
import com.example.greg3d.taskdispatcher.framework.factory.ViewFactory;
import com.example.greg3d.taskdispatcher.framework.helpers.ViewHelper;
import com.example.greg3d.taskdispatcher.helpers.GridViewHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by greg3d on 28.10.17.
 */

public class TaskHistoryActivity extends Activity implements View.OnClickListener{

    private static final String LOG_TAG = "TaskHistoryActivity";
    GridViewHelper gridView;

    private static TaskHistoryActivity instance;
    public static TaskHistoryActivity getInstance(){
        return instance;
    }
    private View view;
    private Controls controls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        instance = this;
        gridView = new GridViewHelper(this, R.id.gvHistory)
                .setAdapter(new CellAdapter(this, getfilteredByMonthRecords(new Date())));

        controls = new Controls();
        ActivityFactory.InitActivity(this, controls);
        ActivityFactory.setListener(this, controls);
        //ActivityFactory.InitFonts(this,controls, CssManager.getEditButtonCss());
        controls.filter_DateView.setDate(new Date());
    }

    public TaskHistoryActivity(){}

    public <T extends View.OnClickListener> TaskHistoryActivity(T activity, View view){
        instance = this;
        this.view = view;
        gridView = new GridViewHelper(view, R.id.gvHistory)
                .setAdapter(new CellAdapter(view.getContext(), getfilteredByMonthRecords(new Date())));

        gridView.getGridView()
                .setOnItemLongClickListener(new GridViewListener(gridView));

        controls = new Controls();
        ViewFactory.InitView(view, controls);
        ActivityFactory.setListener(activity, controls);
        controls.filter_DateView.setDate(new Date());
    }

    @Override
    public void onClick(View view) {
        this.view = view;
        this.onClick(this, view);
    }

    public void onClick(Activity activity, View view){
        ViewHelper v = new ViewHelper(view);
        if(v.idEquals(controls.filter_DateView))
            new DatePickerDialogImpl(activity, getFilter(), new FilterByMonthCommand()).show();
        else if(v.idEquals(controls.title_TextView))
            MainActivity.showHistoryFabs();
    }

    private static class GridViewListener implements AdapterView.OnItemLongClickListener{
        private GridViewHelper gridView;

        public GridViewListener(GridViewHelper gridView){
            this.gridView = gridView;
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            MainActivity.showHistoryFabs();
            gridView.getCellHelper().resetSelect();
            gridView.getCellHelper().setSelect(view, l);
            gridView.setSelected(i);
            return false;
        }
    }

    public boolean isSelected(){
        if(!this.gridView.isSelected()) {
            Show.show(view.getContext(), "Запись не выбрана");
            return false;
        }
        return true;
    }

    // TODO
    public static TaskHistoryModel getSelectedObject(){
        return (TaskHistoryModel)instance.gridView.getSelectedObject();
    }

    public static void refresh(){
        instance.gridView.setAdapter(new CellAdapter(instance.view.getContext(), getfilteredByMonthRecords(instance.getFilter())));
        instance.gridView.setUnSelected();
    }

    public static List<TaskHistoryModel> getfilteredByMonthRecords(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        Date endDate = calendar.getTime();
        return DBController.getHistoryRecordsByMonth(startDate, endDate);
    }

    public static void setFilter(Date date){
        instance.controls.filter_DateView.setDate(date);
    }

    private Date getFilter(){
        return instance.controls.filter_DateView.getDate();
    }
}
