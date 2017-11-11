package com.example.greg3d.taskdispatcher.framework.factory;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.greg3d.taskdispatcher.css.BaseCss;
import com.example.greg3d.taskdispatcher.elements.BaseElement;
import com.example.greg3d.taskdispatcher.framework.annotations.Animation;
import com.example.greg3d.taskdispatcher.framework.annotations.CSS;
import com.example.greg3d.taskdispatcher.framework.annotations.DateFormated;
import com.example.greg3d.taskdispatcher.framework.annotations.FindBy;
import com.example.greg3d.taskdispatcher.framework.annotations.SetView;
import com.example.greg3d.taskdispatcher.framework.interfaces.IDateFormated;
import com.example.greg3d.taskdispatcher.framework.interfaces.ISetAnimation;
import com.example.greg3d.taskdispatcher.framework.interfaces.ISetView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by greg3d on 30.04.17.
 */
public class ActivityFactory {

    public static <T extends View> void InitActivity(T activity, Object conteiner){
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

    public static <T extends Activity> void InitActivity(T activity, Object conteiner){
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
                            if(field.isAnnotationPresent(Animation.class)){
                                int showId = field.getAnnotation(Animation.class).show();
                                int hideId = field.getAnnotation(Animation.class).hide();
                                int clickId = field.getAnnotation(Animation.class).click();
                                if(showId != 0)
                                    ((ISetAnimation)o).setShowAnimation(AnimationUtils.loadAnimation(activity.getApplication(), showId));
                                if(hideId != 0)
                                    ((ISetAnimation)o).setHideAnimation(AnimationUtils.loadAnimation(activity.getApplication(), hideId));
                                // TODO - + ClickAnimation
                            }
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

    public static <T extends View.OnClickListener> void setListener(T activity, Object conteiner){
        Field[] fields = conteiner.getClass().getDeclaredFields();

        for(Field f: fields){
            try {
                if(f.isAnnotationPresent(FindBy.class)) {
                    if(f.get(conteiner) instanceof BaseElement)
                        ((ISetView) f.get(conteiner)).getView().setOnClickListener(activity);
                    else
                        ((View) f.get(conteiner)).setOnClickListener(activity);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getStackTrace().toString());
            }
        }
    }

    public static <T extends Activity, C extends BaseCss> void InitFonts(T activity, Object conteiner, C font){
        Field[] fields = conteiner.getClass().getDeclaredFields();

        for(Field field: fields){
            try {
                Object o = field.get(conteiner);

                if(o instanceof TextView)
                    InitFonts(activity, (TextView)o, font);

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getStackTrace().toString());
            }
        }
    }

    public static <T extends Activity, C extends BaseCss> void InitFonts(T activity, Object conteiner){
        Field[] fields = conteiner.getClass().getDeclaredFields();

        for(Field field: fields){
            try {
                if(field.isAnnotationPresent(CSS.class)) {
                    Object o = field.get(conteiner);
                    Object css = field.getAnnotation(CSS.class).value();
                    if (o instanceof TextView)
                        try {
                            InitFonts(activity, (TextView) o, ((Class)css).newInstance());
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                }

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getStackTrace().toString());
            }
        }
    }

    public static <T extends Activity, C extends BaseCss> void InitFonts(T activity, TextView textView, C font){
        Typeface type = Typeface.createFromAsset(activity.getAssets(), font.getFont());
        textView.setTypeface(type);
        textView.setTextSize(font.getTextSize());
        textView.setTextColor(font.getTextColor());
        textView.setShadowLayer(
                font.getShadowRadius(),     //float radius
                font.getShadowDx(),         //float dx
                font.getShadowDy(),         //float dy
                font.getShadowColor()       //int color
        );
    }

    public static <T extends Activity, C extends BaseCss> void InitFonts(T activity, TextView textView, Object font){
        Typeface type = Typeface.createFromAsset(activity.getAssets(), ((C)font).getFont());
        textView.setTypeface(type);
        textView.setTextSize(((C)font).getTextSize());
        textView.setTextColor(((C)font).getTextColor());
        textView.setShadowLayer(
                ((C)font).getShadowRadius(),     //float radius
                ((C)font).getShadowDx(),         //float dx
                ((C)font).getShadowDy(),         //float dy
                ((C)font).getShadowColor()       //int color
        );
    }

}