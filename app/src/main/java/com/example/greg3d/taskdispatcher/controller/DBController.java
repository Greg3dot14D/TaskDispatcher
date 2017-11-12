package com.example.greg3d.taskdispatcher.controller;

import android.util.Log;

import com.example.greg3d.taskdispatcher.helpers.DBHelper;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;
import com.example.greg3d.taskdispatcher.model.TaskModel;

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
                //" select * from [TASK_HISTORY_TABLE]" +
                " select TH.ID, TH.TASK_ID, TH.ACTIVE_STATE, T.NAME, TH.START_DATE, TH.END_DATE, TH.LAST_DATE, TH.DELETED " +
                " from [TASK_HISTORY_TABLE] TH, [TASK_TABLE] T" +
                " where TH.START_DATE >= %s" +
                " and TH.TASK_ID = T.ID " +
                " and TH.END_DATE <= %s " +
                " and (TH.END_DATE - TH.START_DATE) > 0 " +
                " and TH.DELETED < 1 " +
                " ORDER BY TH.END_DATE DESC"
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

    public static void updateLastDateForTask(TaskModel model) {
        String getLastHistoryRecord = String.format(
                " select * from [TASK_HISTORY_TABLE] where START_DATE = " +
                " (select max(START_DATE) from [TASK_HISTORY_TABLE] where TASK_ID = %s and DELETED = 0) " +
                " and TASK_ID = %s and DELETED = 0"
                , model.id
                , model.id);
        Log.d("LLL", getLastHistoryRecord);

        TaskHistoryModel lastHistory = DBHelper.getRecords(TaskHistoryModel.class, getLastHistoryRecord).get(0);
        model.lastDate = lastHistory.lastDate;
        DBHelper.getInstance().editRecord(model);
    }
}
