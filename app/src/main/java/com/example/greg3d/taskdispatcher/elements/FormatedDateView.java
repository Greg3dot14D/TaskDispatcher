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
public class FormatedDateView <T extends TextView> extends BaseElement implements IDateFormated, ISetView {

    private String dateFormat = DateFormats.DATE_TIME_FORMAT;

    public FormatedDateView(Object view){
        super(view);
    }

    private T getWrappedElement(){return (T)this.view;}

    public void setDate(Date date){
        ((T)this.view).setText(Tools.dateTimeToString(date, this.dateFormat));
    }

    public void setText(String text){
        getWrappedElement().setText(text);
    }

    public void setEmptyText(){
        // "HH:mm:ss yyyy-MM-dd"
        String e = "_";
        getWrappedElement().setText(this.dateFormat
                .replace("H",e)
                .replace("m",e)
                .replace("s",e)
                .replace("y",e)
                .replace("M",e)
                .replace("d",e)
        );
    }

    public Date getDate(){
        return Tools.stringToDate(getWrappedElement().getText().toString(), this.dateFormat);
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
