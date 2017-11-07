package com.example.greg3d.taskdispatcher.helpers;

import android.os.Environment;
import android.util.Log;

import com.example.greg3d.taskdispatcher.constants.Settings;
import com.example.greg3d.taskdispatcher.framework.annotations.Name;
import com.example.greg3d.taskdispatcher.model.BaseModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by greg3d on 24.02.17.
 */
public class CSVHelper {

    private static CSVHelper instance;
    private String LOG_TAG = "CSVHELPER";

    private String DIR_SD = Settings.EXTERNAL_FILES_DIRECTORY;
    //private String DIR_SD = "Download/android";
    private String FILENAME_SD = "fakedata.csv";

    public static CSVHelper getInstance(){
        if(instance == null)
            instance = new CSVHelper();
        return instance;
    }

    public List<String> getFileNameList(){
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            throw new RuntimeException("SD-карта не доступна: " + Environment.getExternalStorageState());
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();

        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);

        if(!sdPath.exists())
            sdPath.mkdir();

        String[] list = sdPath.list();

        for(int i=0; i < list.length; i ++)
            Log.d(LOG_TAG, list[i]);

        return Arrays.asList(sdPath.list());

        // добавляем свой каталог к пути
        //sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
    }

    // Чтение данных с SD карты
    public <T extends BaseModel> List<T> readFileSD(String fileName, Class<T> clazz) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            throw new RuntimeException("SD-карта не доступна: " + Environment.getExternalStorageState());
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, fileName);
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            return readDateRecords(br, clazz);
            // читаем содержимое
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("readFileSD null");
    }
//
//    public List<DateRecord> readFileSD() {
//        return readFileSD(FILENAME_SD);
//    }
//
//
//    // Чтение данных из внутренней памяти
//    public List<DateRecord> readCsv(Context context) {
//
//            InputStream csvStream = context.getResources().openRawResource(R.raw.fakedata);
//            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
//            BufferedReader br = new BufferedReader(csvStreamReader);
//
//            return readDateRecords(br);
//    }
//
    private <T extends BaseModel> List<T> readDateRecords(BufferedReader br, Class<T> clazz){
        List<T> resultList = new ArrayList<>();

        try {
            String[] headers = null;
            String[] line = null;
            String str = null;
            boolean readHeaders = true;
            while ((str = br.readLine()) != null){
                if(readHeaders){
                    headers = str.split(";");
                    readHeaders = false;
                }
                else {
                    T record = clazz.newInstance();
                    line = str.split(";");
                    for (int i = 0; i < headers.length; i++)
                        record.setValue(headers[i].replace("\t",""), line[i].replace("\t",""));
                    resultList.add(record);
                }
            }
        } catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Запись
    **/
    // Запись данных на SD карту
    public <T extends BaseModel> void writeFileSD(String fileName, List<T> records) {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            throw new RuntimeException("SD-карта не доступна: " + Environment.getExternalStorageState());
        }

        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, fileName);
        try {
            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            // пишем данные
            writeDateRecords(bw, records);
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage());
            throw new RuntimeException("Write files exception");
        }
    }

//    private <T extends BaseModel> String getFileName(List<T> model){
//        return model.get(0).getClassName();
//    }
//
//    // Запись данных на SD карту
//    public void writeFileSD(List<DateRecord> records) {
//        writeFileSD("1_" + FILENAME_SD, records);
//    }
//
    private <T extends BaseModel>void writeDateRecords(BufferedWriter bw, List<T> records){
        try {
            for(int i = 0; i < records.size(); i ++){
                if(i == 0)
                    bw.write(headersToString(records.get(i)));
                bw.write(modelToString(records.get(i)));
                if(i< records.size() - 1)
                    bw.write("\n");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
        }
    }

    private <T extends BaseModel> String headersToString(T model){
        StringBuilder sb = new StringBuilder();
        List<Field> fields = model.getFieldList();
        for(Field f: fields){
            sb.append("\t").append(f.getAnnotation(Name.class).value()).append(";");
        }
        return sb.append("\n").toString();
    }

    private <T extends BaseModel> String modelToString(T model){
        StringBuilder sb = new StringBuilder();
        List<Field> fields = model.getFieldList();
        for(Field f: fields){
            try {
                Object o = f.get(model);
                String value = "";
                if(o == null)
                    value = "";
                else if (f.getType().equals(Date.class))
                    // TODO - Date to Long
                    //value = Tools.dateTimeToString((Date)f.get(model));
                    value = String.valueOf(((Date)f.get(model)).getTime());
                else
                    value = f.get(model).toString();
                sb.append("\t").append(value).append(";");
            }catch(IllegalAccessException e){}
        }
        return sb.toString();
    }
}
