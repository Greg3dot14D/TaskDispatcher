package com.example.greg3d.taskdispatcher.framework.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.greg3d.taskdispatcher.model.BaseModel;

import java.util.List;

/**
 * Created by greg3d on 29.10.17.
 */

public class BaseSpinerObjectAdapter<T extends BaseModel> extends ArrayAdapter<T> {

    public BaseSpinerObjectAdapter(@NonNull Context context, Class<T> clazz) {
        super(context, android.R.layout.simple_spinner_item, getData(clazz));
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    protected static <T> List<T> getData(Class<T> clazz){
        return null;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
