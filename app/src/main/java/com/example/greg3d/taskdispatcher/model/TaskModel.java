package com.example.greg3d.taskdispatcher.model;

import com.example.greg3d.taskdispatcher.framework.annotations.Name;

import java.util.Date;

/**
 * Created by greg3d on 06.11.17.
 */
@Name("TASK_TABLE")
public class TaskModel extends BaseModel{

    // "Id"
    @Name("ID")
    public Integer id;

    //  "Название"
    @Name("NAME")
    public String name;

    //  "Дата Обновления"
    @Name("LAST_DATE")
    public Date lastDate;

    // "deleted = 1 - запись удалена"
    @Name("DELETED")
    public Integer deleted = 0;
}
