package com.example.greg3d.taskdispatcher.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.example.greg3d.taskdispatcher.commands.IDateCommand;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by greg3d on 23.04.17.
 */
public class DatePickerDialogImpl{

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);;
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    public static String dateMask = "yyyy-MM-dd";

    private DatePickerDialog tpd;
    private Date date;

    private IDateCommand command;

    DatePickerDialog.OnDateSetListener CallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int inYear, int inMonth,
                              int inDay) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(inYear, inMonth, inDay, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            command.execute(calendar);
        }
    };

    public DatePickerDialogImpl(final Activity activity, Date date, IDateCommand command)
    {
        this.command = command;
        this.date = date;

        calendar.setTime(date);

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);

        tpd = new DatePickerDialog(activity, CallBack, this.year, this.month, this.day);
    }
    /** Called when the activity is first created. */

    public void showCurrentDate(){
        tpd.updateDate(this.year, this.month, this.day);
        tpd.show();
    }

    public void show(){
        this.show(this.date);
    }

    public void show(Date date){
        calendar.setTime(date);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        tpd.updateDate(this.year, this.month, this.day);
        tpd.show();
    }
}
