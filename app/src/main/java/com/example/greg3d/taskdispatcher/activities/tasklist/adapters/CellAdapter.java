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
import com.example.greg3d.taskdispatcher.helpers.Tools;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;

import java.util.List;

/**
 * Created by greg3d on 01.10.17.
 */
public class CellAdapter extends ArrayAdapter<TaskHistoryModel>
{
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
        return getView(convertView, cell);
    }

    public static View getView(View convertView, TaskHistoryModel cell) {

        TaskHistoryView controls = new TaskHistoryView();

        ActivityFactory.InitActivity(convertView, controls);

        controls.id_TextView.setValue(cell.taskId);
        controls.historyId_TextView.setValue(cell.id);
        controls.startDate_DateView.setDate(cell.startDate);
        if(cell.activeState == State.IS_ACTIVE) {
            controls.endDate_DateView.setEmptyText();
            controls.status_TextView.setText("АКТИВНА");
            controls.duration_DateView.setEmptyText();
        }
        else {
            controls.endDate_DateView.setDate(cell.endDate);
            controls.status_TextView.setText("не активна");
            //controls.duration_DateView.setDate();
            controls.duration_TextView.setText(Tools.msToString(cell.endDate.getTime() - cell.startDate.getTime()));
        }
        controls.name_TextView.setText(cell.name);
        // TODO
        return convertView;
    }
}
