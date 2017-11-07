package com.example.greg3d.taskdispatcher.controller;

import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;

import java.util.Date;
import java.util.List;

/**
 * Created by greg3d on 16.10.17.
 *
 */
public class DBController {

    String LOG_TAG = "DBController";

    private static DBController instance;

    public static DBController getInstance(){
        if(instance == null)
            instance = new DBController();
        return instance;
    }

    public static List<TaskHistoryModel> getHistoryRecordsByMonth(Date startDate, Date endDate) {
        String query = String.format(
                " select * from [TASK_HISTORY_TABLE]" +
                " where START_DATE >= %s" +
                " and END_DATE <= %s " +
                " and (END_DATE - START_DATE) > 0 " +
                " and DELETED < 1 " +
                " ORDER BY START_DATE DESC"
                    , startDate.getTime()
                    , endDate.getTime()
                );
        return DBHelper.getRecords(TaskHistoryModel.class, query);
    }

    public static List<TaskHistoryModel> getLastRecords() {
        String query =
                        " select H.* from [TASK_HISTORY_TABLE] H, [TASK_TABLE] T" +
                        " where H.TASK_ID = T.ID" +
                        " and H.LAST_DATE = T.LAST_DATE " +
                        " and T.DELETED < 1 " +
                        " ORDER BY T.NAME ";
        return DBHelper.getRecords(TaskHistoryModel.class, query);
    }
}
