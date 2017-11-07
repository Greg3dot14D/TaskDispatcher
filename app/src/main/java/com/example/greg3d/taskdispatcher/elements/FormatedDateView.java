package com.example.greg3d.taskdispatcher.elements;

import android.widget.TextView;

import com.example.greg3d.taskdispatcher.constants.DateFormats;
import com.example.greg3d.taskdispatcher.framework.annotations.SetView;
import com.example.greg3d.taskdispatcher.framework.interfaces.IDateFormated;
import com.example.greg3d.taskdispatcher.framework.interfaces.ISetView;
import com.example.greg3d.taskdispatcher.helpers.Tools;

import java.util.Date;

/**
 *
 * Created by greg3d on 05.11.17.
 *
 */
@SetView(TextView.class)
public class FormatedDateView extends BaseElement implements IDateFormated, ISetView {

    private String dateFormat = DateFormats.DATE_TIME_FORMAT;

    public FormatedDateView(Object view){
        super(view);
    }

    public void setDate(Date date){
        this.view.setText(Tools.dateTimeToString(date, this.dateFormat));
    }

    public void setText(String text){
        this.view.setText(text);
    }

    public void setEmptyText(){
        // "HH:mm:ss yyyy-MM-dd"
        String e = "_";
        this.view.setText(this.dateFormat
                .replace("H",e)
                .replace("m",e)
                .replace("s",e)
                .replace("y",e)
                .replace("M",e)
                .replace("d",e)
        );
    }

    public Date getDate(){
        return Tools.stringToDate(this.view.getText().toString(), this.dateFormat);
    }

    @Override
    public String getDateFormat() {
        return this.getDateFormat();
    }

    @Override
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }


}
