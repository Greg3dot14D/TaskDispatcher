package com.example.greg3d.taskdispatcher.helpers;

import android.app.Activity;
import android.content.Intent;

import com.example.greg3d.taskdispatcher.activities.taskedit.TaskEditActivity;


/**
 * Created by greg3d on 26.04.17.
 */
public class ActivitiesManager {
    public static void startTaskEditActivity(Activity activity){
        Intent intent = new Intent(activity , TaskEditActivity.class);
        activity.startActivity(intent);
    }

    // TODO
//    public static void startCureIntakeActivity(Activity fragment){
//        //Intent intent = new Intent(CureIntakeActivity.getInstance() , CureIntakeActivity.class);
//        Intent intent = new Intent(fragment, CureIntakeActivity.class);
//        fragment.startActivity(intent);
//    }
//
//    public static void startCureHistoryActivity(Activity fragment){
//        Intent intent = new Intent(fragment , CureHistoryActivity.class);
//        fragment.startActivity(intent);
//    }
//
//    public static void startCureEditActivity(Activity activity, long id){
//        Bundle bundle = new Bundle();
//        bundle.putLong("id", id);
//        Intent intent = new Intent(activity, CureEditActivity.class);
//        //intent.replaceExtras(bundle);
//        intent.putExtra("id", id);
//        activity.startActivity(intent);
//    }
//
//    public static void startEditIntakeActivity(Activity fragment){
//        Intent intent = new Intent(fragment, EditIntakeActivity.class);
//        fragment.startActivity(intent);
//    }

//
//    public static void startFileListActivity(Fragment fragment){
//        Intent intent = new Intent(VisitTimeFixerActivity.instance , FileListActivity.class);
//        fragment.startActivity(intent);
//    }
//    public static void startEditRecordActivity(Activity activity, long id){
//        Bundle bundle = new Bundle();
//        bundle.putLong("id", id);
//        Intent intent = new Intent(activity, EditRecordActivity.class);
//        //intent.replaceExtras(bundle);
//        intent.putExtra("id", id);
//        activity.startActivity(intent);
//    }

}
