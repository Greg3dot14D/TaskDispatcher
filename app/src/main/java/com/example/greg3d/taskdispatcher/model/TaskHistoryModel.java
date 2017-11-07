package com.example.greg3d.taskdispatcher.model;

import com.example.greg3d.taskdispatcher.framework.annotations.Name;

import java.util.Date;

/**
 * Created by greg3d on 06.11.17.
 */
@Name("TASK_HISTORY_TABLE")
public class TaskHistoryModel extends BaseModel{
    // "Id"
    @Name("ID")
    public Integer id;

    //  "Id лекарства"
    @Name("TASK_ID")
    public Integer taskId;

    //  "Состояние активности"
    @Name("ACTIVE_STATE")
    public Integer activeState = 0;

    //  "Название" - дублируем, для упрощения работы с моделями данных
    @Name("NAME")
    public String name;

    //  "Дата старта задачи"
    @Name("START_DATE")
    public Date startDate;

    //  "Дата завершения задачи"
    @Name("END_DATE")
    public Date endDate;

    //  "Дата обновления"
    @Name("LAST_DATE")
    public Date lastDate;

    // "deleted = 1 - запись удалена"
    @Name("DELETED")
    public Integer deleted = 0;
}
