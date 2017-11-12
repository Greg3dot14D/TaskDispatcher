package com.example.greg3d.taskdispatcher.activities.tasklist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.activities.tasklist.controls.TaskHistoryView;
import com.example.greg3d.taskdispatcher.constants.State;
import com.example.greg3d.taskdispatcher.controller.DBController;
import com.example.greg3d.taskdispatcher.framework.factory.ActivityFactory;
import com.example.greg3d.taskdispatcher.helpers.GridViewHelper;
import com.example.greg3d.taskdispatcher.helpers.Tools;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;

import java.util.Date;
import java.util.List;

/**
 * Created by greg3d on 01.10.17.
 */
public class CellAdapter extends ArrayAdapter<TaskHistoryModel>
{
    private static GridViewHelper gridViewHelper;

    public CellAdapter(Context context, GridViewHelper gridViewHelper) {
        super(context, R.layout.task_cell, getData());
        this.gridViewHelper = gridViewHelper;
    }

    public CellAdapter(Context context) {
        super(context, R.layout.task_cell, getData());
    }

    private static List<TaskHistoryModel> getData(){
        return DBController.getLastRecords();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskHistoryModel cell = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.task_cell, null);
        return getView(position, convertView, cell);
    }

    public static View getView(int position, View convertView, TaskHistoryModel cell) {

        TaskHistoryView controls = new TaskHistoryView();

        ActivityFactory.InitActivity(convertView, controls);
        ActivityFactory.InitFonts(convertView.getContext(), controls);

        controls.startDate_DateView.setDate(cell.startDate);

        setBackgroundResource(position, convertView, R.drawable.side_default_cell);

        if(cell.activeState == State.IS_ACTIVE) {
            setBackgroundResource(position, convertView, R.drawable.side_active_cell);
            controls.endDate_DateView.setEmptyText();
            controls.status_TextView.setText("АКТИВНА");
            //controls.duration_DateView.setEmptyText();
            controls.duration_DateView.setDate(Tools.getDifTime(cell.startDate, new Date()));
        }
        else if(cell.endDate.equals(cell.startDate)){
            controls.status_TextView.setText("не активна");
            controls.startDate_DateView.setEmptyText();
            controls.endDate_DateView.setEmptyText();
            controls.duration_DateView.setEmptyText();
        }
        else{
            controls.status_TextView.setText("не активна");
            controls.endDate_DateView.setDate(cell.endDate);
            // TODO - меняем ms на getDifTime
            controls.duration_DateView.setDate(Tools.getDifTime(cell.startDate, cell.endDate));
            //controls.duration_TextView.setText(Tools.msToString(cell.endDate.getTime() - cell.startDate.getTime()));
        }
        controls.name_TextView.setText(cell.name);
        // TODO
        return convertView;
    }

    private static void setBackgroundResource(int position, View convertView, int resourceId){
        if(gridViewHelper != null)
            if(gridViewHelper.isSelected() && gridViewHelper.getSelectedPosition() == position) {
                convertView.setBackgroundResource(R.drawable.side_selected_cell);
                return;
            }
        convertView.setBackgroundResource(resourceId);
    }
}
