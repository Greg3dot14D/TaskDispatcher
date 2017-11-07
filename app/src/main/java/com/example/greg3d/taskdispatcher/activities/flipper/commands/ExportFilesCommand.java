package com.example.greg3d.taskdispatcher.activities.flipper.commands;


import com.example.greg3d.taskdispatcher.commands.ICommand;
import com.example.greg3d.taskdispatcher.controller.CSVController;

/**
 * Created by greg3d on 06.11.17.
 */

public class ExportFilesCommand implements ICommand {
    @Override
    public void execute() {
        CSVController.writeTablesToSD();
    }
}
