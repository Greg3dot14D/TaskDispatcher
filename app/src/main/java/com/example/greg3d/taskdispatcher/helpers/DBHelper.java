package com.example.greg3d.taskdispatcher.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.greg3d.taskdispatcher.framework.annotations.Name;
import com.example.greg3d.taskdispatcher.model.BaseModel;
import com.example.greg3d.taskdispatcher.model.TaskHistoryModel;
import com.example.greg3d.taskdispatcher.model.TaskModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by greg3d on 24.02.17.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String LOG_TAG = "DB_HELPER";
    private SQLiteDatabase db;

    private static DBHelper instance;

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
        Log.d(LOG_TAG, "DBHelper Starts");
        this.db = this.getWritableDatabase();
        instance = this;
        //new FakeData(this).createFakes();
    }

    public static DBHelper getInstance(){
        return instance;
    }

    public static void execSQL(String sql){
        instance.db.execSQL(sql);
    }

    public static int getCount(String sql){
        Cursor c = instance.db.rawQuery(sql, null);
        return c.getCount();
    }

    public static SQLiteDatabase getDb(){
        return instance.db;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицы с полями
        this.db = sqLiteDatabase;

        this.createTable(new TaskModel());
        this.createTable(new TaskHistoryModel());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public <T extends BaseModel> void createTable(T model){
        String query = String.format(
                "CREATE TABLE [%s] ([ID] integer NOT NULL PRIMARY KEY AUTOINCREMENT %s);"
                , model.getClassName()
                , getCreateTableFieldsScript(model)
        );
        db.execSQL(query);
    }

    public <T extends BaseModel> void deleteRecord(T model){
        String query = String.format(
                "UPDATE [%s] SET DELETED = 1 WHERE ID = %s"
                , model.getClassName()
                , model.getValue("ID")
        );
        Log.d(LOG_TAG, "DELETE ->" + query);
        db.execSQL(query);
    }

    public <T extends BaseModel> void insertRecord(T model){
        Log.d(LOG_TAG, "do insert");
        //        "insert into [INTAKE_HISTORY_TABLE] " +
        //        " ([SCHEME_ID], [INTAKE_ID_DAYE_NUM], [STATUS], [ACTION_DATE]) " +
        //        " values (%s, %s, %s, '%s')",
        String query = String.format(
                "insert into [%s] %s;"
                , model.getClassName()
                , getInsertRecordFieldsScript(model)
        );
        db.execSQL(query);

        Log.d(LOG_TAG, "done insert");
    }

    public <T extends BaseModel> T getLastRecord(T model){
        String query = String.format(
                "SELECT * FROM [%s] WHERE ID = (SELECT max(ID) FROM [%s])"
                , model.getClassName()
                , model.getClassName());
        return getRecords(model, query).get(0);
    }

    public <T extends BaseModel> T getRecord(T model){
        String query = String.format(
                "SELECT * FROM [%s] WHERE ID = %s"
                , model.getClassName()
                , model.getValue("ID"));
        Log.d(LOG_TAG, "getRecord -> " + query);
        return getRecords(model, query).get(0);
    }

    public static <T extends BaseModel> T getRecordById(Class<T> clazz, Integer id){
        try {
            return getRecordById(clazz.newInstance(), id);
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static <T extends BaseModel> T getRecordById(T model, Integer id){
        String query = String.format(
                "SELECT * FROM [%s] WHERE ID = %s"
                //, clazz.getAnnotation(Name.class).value()
                , model.getClassName()
                , id.toString());
        return getRecords(model, query).get(0);
    }

        public <T extends BaseModel> void updateRecord(T model){
        String query = String.format(
                "update [%s] set %s"
                , model.getClassName()
                , getUpdateRecordFieldsScript(model)
        );
        Log.d(LOG_TAG, query);
        db.execSQL(query);
    }

    private <T extends BaseModel> String getUpdateRecordFieldsScript(T model){
        // "update [VISIT_DATETIME_TABLE] " +
        // " set [DATETIME_IN] = '%s', " +
        // " [DATETIME_OUT] = '%s' " +
        // " where [DATETIME_IN] like '%s%%'"

        List<Field> list = model.getFieldList();

        String result = "";
        Integer id = 0;
        for(Field f: list){
             {
                Class<?> type = f.getType();
                if(f.isAnnotationPresent(Name.class))
                try {
                    if("ID".equals(f.getAnnotation(Name.class).value()))
                        id = (Integer) f.get(model);
                    else if (type.equals(String.class))
                        result += String.format(",[%s] = '%s'", f.getAnnotation(Name.class).value(), f.get(model).toString());
                    else if (type.equals(Date.class))
                    // TODO - Date to long
                    //    result += String.format(",[%s] = '%s'", f.getAnnotation(Name.class).value(), Tools.dateTimeToString((Date)f.get(model)));
                        result += String.format(",[%s] = %s", f.getAnnotation(Name.class).value(), ((Date)f.get(model)).getTime());
                    else
                        result += String.format(",[%s] = %s", f.getAnnotation(Name.class).value(), f.get(model));
                }catch(IllegalAccessException e){
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        if(id == 0)
            throw new RuntimeException("update id = 0");
        return String.format("%s where ID = %s", result.substring(1), id);
    }

    public <T extends BaseModel> void editRecord(T model){
        String query = String.format(
                "update [%s] set %s"
                , model.getClassName()
                , getEditRecordFieldsScript(model)
        );
        Log.d(LOG_TAG, "edit ->" + query);
        db.execSQL(query);
    }

    private <T extends BaseModel> String getEditRecordFieldsScript(T model){
        List<Field> list = model.getFieldList();

        String result = "";
        Integer id = 0;
        for(Field f: list){
            {
                Class<?> type = f.getType();
                if(f.isAnnotationPresent(Name.class))
                try {
                        Object value = f.get(model);
                        if(value != null) {
                            if ("ID".equals(f.getAnnotation(Name.class).value()))
                                id = (Integer) value;
                            else if (type.equals(String.class))
                                result += String.format(",[%s] = '%s'", f.getAnnotation(Name.class).value(), value.toString());
                            else if (type.equals(Date.class))
                                // TODO - Date to long
                                //result += String.format(",[%s] = '%s'", f.getAnnotation(Name.class).value(), Tools.dateTimeToString((Date) value));
                                result += String.format(",[%s] = %s", f.getAnnotation(Name.class).value(), ((Date) value).getTime());
                            else
                                result += String.format(",[%s] = %s", f.getAnnotation(Name.class).value(), value);
                        }
                }catch(IllegalAccessException e){
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        if(id == 0)
            throw new RuntimeException("update id = 0");
        return String.format("%s where ID = %s", result.substring(1), id);
    }

    private <T extends BaseModel> String getCreateTableFieldsScript(T model){
        String result = "";
        List<Field> list = model.getFieldList();
        for(Field f: list){
            if(f.isAnnotationPresent(Name.class))
            if(!"ID".equals(f.getAnnotation(Name.class).value())) {
                Class<?> type = f.getType();
                // TODO - Date to long
                //if (type.equals(String.class) || type.equals(Date.class))
                if (type.equals(String.class))
                    result += String.format(", [%s] TEXT", f.getAnnotation(Name.class).value());
                if (type.equals(Double.class))
                    result += String.format(", [%s] REAL DEFAULT 0.0", f.getAnnotation(Name.class).value());
                // TODO - Date to long
                //if (type.equals(Integer.class) || type.equals(Long.class))
                if (type.equals(Integer.class) || type.equals(Long.class) || type.equals(Date.class))
                    result += String.format(", [%s] integer", f.getAnnotation(Name.class).value());
            }
        }
        return result;
    }

    private <T extends BaseModel> String getInsertRecordFieldsScript(T model){
    //        "insert into [INTAKE_HISTORY_TABLE] " +
    //        " ([SCHEME_ID], [INTAKE_ID_DAYE_NUM], [STATUS], [ACTION_DATE]) " +
    //        " values (%s, %s, %s, '%s')"

        List<Field> list = model.getFieldList();

        String fields = "";
        String values = "";
        for(Field f: list){
            if(f.isAnnotationPresent(Name.class))
            if(!"ID".equals(f.getAnnotation(Name.class).value())) {
                Class<?> type = f.getType();
                try {
                    fields += String.format(",[%s]", f.getAnnotation(Name.class).value());
                    if (type.equals(String.class))
                        values += String.format(",'%s'", f.get(model).toString());
                    else if (type.equals(Date.class))
                        // TODO - Date to long
                        //values += String.format(",'%s'", Tools.dateTimeToString((Date)f.get(model)));
                        values += String.format(",%s", ((Date)f.get(model)).getTime());
                    else
                        values += String.format(",%s", f.get(model));
                }catch(IllegalAccessException e){
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        return String.format("(%s) values (%s)", fields.substring(1), values.substring(1));
    }

    public static <T extends BaseModel> List<T> getRecords(Class<T> clazz){
        return getRecords(clazz, String.format(" SELECT * FROM [%s] WHERE DELETED = 0", clazz.getAnnotation(Name.class).value()));
    }

    public static <T extends BaseModel> List<T> getRecords(T model, String query){
        return (List<T>)getRecords(model.getClass(), query);
    }

    public static <T extends BaseModel> List<T> getRecords(Class<T> clazz, String query){
            List<T> list = new ArrayList<>();
            Cursor cursor = DBHelper.getDb().rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    try {
                        list.add(fillModel(cursor, clazz.newInstance()));
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e.getMessage());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            return list;
//            Cursor cursor = DBHelper.getDb().rawQuery(query, null);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    model = clazz.newInstance();
//                    for (Field f : fields) {
//                        if (f.isAnnotationPresent(Name.class)) {
//                            if (f.getType().equals(Integer.class))
//                                f.set(model, Integer.valueOf(cursor.getString(cursor.getColumnIndex(f.getAnnotation(Name.class).value()))));
//                            else if (f.getType().equals(Date.class))
//                                f.set(model, Tools.stringToDateTime(cursor.getString(cursor.getColumnIndex(f.getAnnotation(Name.class).value()))));
//                            else if (f.getType().equals(Double.class))
//                                f.set(model, Double.valueOf(cursor.getString(cursor.getColumnIndex(f.getAnnotation(Name.class).value()))));
//                            else
//                                f.set(model, cursor.getString(cursor.getColumnIndex(f.getAnnotation(Name.class).value())));
//                        }
//                    }
//                    list.add(model);
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//            return list;
//        }catch(IllegalAccessException e){
//            throw new RuntimeException(e.getMessage());
//        }catch(InstantiationException e){
//            throw new RuntimeException(e.getMessage());
//        }
    }

    private static <T extends BaseModel> T fillModel(Cursor cursor, T model){
        List<Field> fields = model.getFieldList();

        for(Field f: fields){
            if(f.isAnnotationPresent(Name.class)) {
                String fieldName = f.getAnnotation(Name.class).value();
                model.setValue(fieldName, cursor.getString(cursor.getColumnIndex(fieldName)));
            }
        }
        return model;
    }


    // TEXT как строка формата ISO8601 ("YYYY-MM-DD HH:MM:SS.SSS").



    public <T extends BaseModel> void dropTable(T model){
        try {
            this.db.execSQL(String.format("drop table [%s]", model.getClassName()));
        }catch(Exception e){
            //throw new RuntimeException(e.getMessage());
        }

    }

    public <T extends BaseModel> void deleteRecords(T model){
        this.db.execSQL(String.format("delete from [%s]", model.getClassName()));
    }





}
