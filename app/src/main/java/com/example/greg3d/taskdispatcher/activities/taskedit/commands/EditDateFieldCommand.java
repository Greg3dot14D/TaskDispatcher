package com.example.greg3d.taskdispatcher.activities.taskedit.commands;

import com.example.greg3d.taskdispatcher.commands.IDateCommand;
import com.example.greg3d.taskdispatcher.elements.FormatedDateView;

import java.util.Calendar;

/**
 * Created by greg3d on 12.11.17.
 */

public class EditDateFieldCommand implements IDateCommand{

    private FormatedDateView view;

    public EditDateFieldCommand(FormatedDateView view){
        this.view = view;
    }

    @Override
    public void execute(Calendar calendar) {
        view.setDate(calendar.getTime());
    }
}
