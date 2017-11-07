package com.example.greg3d.taskdispatcher.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by greg3d on 22.09.17.
 */
public class MessageDialog {

    public MessageDialog(final Activity activity, String title, String message){
        AlertDialog.Builder ad = new AlertDialog.Builder(activity);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setIcon(android.R.drawable.ic_dialog_alert);
        ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        ad.show();
    }
}
