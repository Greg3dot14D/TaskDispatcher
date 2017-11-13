package com.example.greg3d.taskdispatcher.timer;

import java.util.Timer;

/**
 * Created by greg3d on 11.06.17.
 */
public class TimerManager {

    private static Timer timer;

    public static void startTimer(SpentTimerTaskList task){
        if(timer == null)
            timer = new Timer();
        //timer.schedule( task, 1000, 1000);
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

//    public static void addTimer(){
////        if(timer == null)
////            timer = new Timer();
////        timer.schedule( new MissingTimerTask(), 1000, 1000);
//    }

    public static void stopTimer(){
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
