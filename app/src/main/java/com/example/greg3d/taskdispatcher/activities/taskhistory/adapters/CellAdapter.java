package com.example.greg3d.taskdispatcher.activities.taskhistory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;

import java.util.List;

/**
 * Created by greg3d on 01.10.17.
 */
public class CellAdapter extends ArrayAdapter<TaskHistoryModel>
{
    public CellAdapter(Context context, List<TaskHistoryModel> data) {
        super(context, R.layout.task_cell, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskHistoryModel cell = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.task_cell, null);

        return com.example.greg3d.taskdispatcher.activities.tasklist.adapters.CellAdapter
                .getView(convertView, cell);
    }
}
