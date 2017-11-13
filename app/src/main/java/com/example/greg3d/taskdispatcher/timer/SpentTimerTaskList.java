package com.example.greg3d.taskdispatcher.timer;

import com.example.greg3d.taskdispatcher.activities.flipper.MainActivity;
import com.example.greg3d.taskdispatcher.elements.FormatedDateView;
import com.example.greg3d.taskdispatcher.helpers.Tools;

import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

/**
 * Created by greg3d on 11.06.17.
 */
public class SpentTimerTaskList extends TimerTask {

    private static HashMap<Integer, TimerObject> timerObjectMap;

    public SpentTimerTaskList(){
        timerObjectMap = new HashMap<>();
    }

    public static void addTimer(int id, FormatedDateView daterTimeView, Date startDate){
        if(timerObjectMap == null)
            return;
        timerObjectMap.put(id, new TimerObject(daterTimeView, startDate));
    }

    public static void removeTimer(int id){
        if(timerObjectMap == null)
            return;
        timerObjectMap.remove(id);
    }

    public static class TimerObject{
        public FormatedDateView daterTimeView;
        public Date startDate;

        public TimerObject(FormatedDateView daterTimeView, Date startDate){
            this.daterTimeView = daterTimeView;
            this.startDate = startDate;
        }
    }

    @Override
    public void run() {
        MainActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                for(int key: timerObjectMap.keySet())
//                    timerObjectMap.get(key).daterTimeView.setDate(Tools.getDifTime(timerObjectMap.get(key).startDate, new Date()));
                for(TimerObject o: timerObjectMap.values())
                    o.daterTimeView.setDate(Tools.getDifTime(o.startDate, new Date()));
                //daterTimeView.setDate(Tools.getDifTime(startDate, new Date()));
            }
        });
    }
}
