package com.example.greg3d.taskdispatcher.framework.factory;

import android.view.View;

import com.example.greg3d.taskdispatcher.framework.annotations.DateFormated;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;
import com.example.greg3d.taskdispatcher.framework.annotations.SetView;
import com.example.greg3d.taskdispatcher.framework.interfaces.IDateFormated;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by greg3d on 01.10.17.
 */
public class ViewFactory {

//    public static <T extends View> void InitView(T activity, Object conteiner){
//        Field[] fields = conteiner.getClass().getDeclaredFields();
//
//        for(Field field: fields){
//            if(field.isAnnotationPresent(FindBy.class)){
//                int id = field.getAnnotation(FindBy.class).value();
//
//                try {
//                    field.set(conteiner, activity.findViewById(id));
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e.getStackTrace().toString());
//                }
//            }
//        }
//    }

    public static <T extends View> void InitView(T activity, Object conteiner){
        Field[] fields = conteiner.getClass().getDeclaredFields();
        String fieldName = "";
        for(Field field: fields){
            try {
                if(field.isAnnotationPresent(FindBy.class)){
                    int id = field.getAnnotation(FindBy.class).value();
                    if(field.getType().isAnnotationPresent(SetView.class)){
                        try {
                            Object o = field.getType().getConstructor(Object.class).newInstance(activity.findViewById(id));
                            if(field.isAnnotationPresent(DateFormated.class))
                                ((IDateFormated)o).setDateFormat(field.getAnnotation(DateFormated.class).value());
                            field.set(conteiner, o);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }else
                        field.set(conteiner, activity.findViewById(id));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getStackTrace().toString());
            }
        }
    }
}
