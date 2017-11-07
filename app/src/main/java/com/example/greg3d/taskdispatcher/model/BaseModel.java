package com.example.greg3d.taskdispatcher.model;

import android.util.Log;

import com.example.greg3d.taskdispatcher.framework.annotations.Name;
import com.example.greg3d.taskdispatcher.helpers.Tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by greg3d on 15.10.17.
 */

public class BaseModel {

    public String getClassName(){
        return this.getClass().getAnnotation(Name.class).value();
    }

    public List<Field> getFieldList(){
        Field[] fields = this.getClass().getFields();
        List<Field> list = new ArrayList<>();
        for(Field f: fields){
            list.add(f);
        }
        return list;
    }

    public Object getValue(String fieldName){
        Field[] fields = this.getClass().getFields();
        for(Field f: fields){
            if(f.isAnnotationPresent(Name.class))
                if(fieldName.equals(f.getAnnotation(Name.class).value()))
                try {
                    return f.get(this);
                }catch(IllegalAccessException e){}
        }
        throw new RuntimeException(String.format("No such field [%s] exception", fieldName));
    }


    public void setValue(String fieldName, Object o){
        Field f = this.getFieldByName(fieldName);
        Class<?> type = f.getType();
        try {
            Log.d("r->", "" + f.getName());
            if(type.equals(Integer.class))
                f.set(this, Integer.valueOf(o.toString()));
            else if(type.equals(Date.class))
                // TODO - long to Date
                f.set(this, Tools.longToDate(Long.valueOf(o.toString())));
            else if(type.equals(Double.class))
                f.set(this, Double.valueOf(o.toString()));
            else
                f.set(this, o.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Field getFieldByName(String name){
        Field[] fields = this.getClass().getFields();
        List<Field> list = new ArrayList<>();
        for(Field f: fields){
            if(f.isAnnotationPresent(Name.class))
                if(name.equals(f.getAnnotation(Name.class).value()))
                    return f;
        }
        throw new RuntimeException(String.format("No such filed [%s] exception", name));
    }
}
