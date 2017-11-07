package com.example.greg3d.taskdispatcher.dialog;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by greg3d on 28.10.17.
 */

public class Show {

    public static <T extends Context>void show(T o, String toShow){
        AlertDialog.Builder ad = new AlertDialog.Builder(o);
        ad.setMessage(toShow);
        ad.show();
    }
}
