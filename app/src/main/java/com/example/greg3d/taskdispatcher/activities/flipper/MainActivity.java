package com.example.greg3d.taskdispatcher.activities.flipper;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.activities.flipper.adapters.SamplePagerAdapter;
import com.example.greg3d.taskdispatcher.activities.flipper.commands.ExportFilesCommand;
import com.example.greg3d.taskdispatcher.activities.flipper.commands.ImportFilesCommand;
import com.example.greg3d.taskdispatcher.activities.flipper.controls.MainControls;
import com.example.greg3d.taskdispatcher.activities.taskedit.TaskEditActivity;
import com.example.greg3d.taskdispatcher.activities.taskhistory.TaskHistoryActivity;
import com.example.greg3d.taskdispatcher.activities.tasklist.TaskListActivity;
import com.example.greg3d.taskdispatcher.activities.tasklist.commands.DeleteTaskCommand;
import com.example.greg3d.taskdispatcher.constants.Settings;
import com.example.greg3d.taskdispatcher.constants.State;
import com.example.greg3d.taskdispatcher.dialog.MessageDialog;
import com.example.greg3d.taskdispatcher.dialog.YesNoDialog;
import com.example.greg3d.taskdispatcher.framework.factory.ActivityFactory;
import com.example.greg3d.taskdispatcher.framework.helpers.ViewHelper;
import com.example.greg3d.taskdispatcher.helpers.ActivitiesManager;
import com.example.greg3d.taskdispatcher.helpers.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private TaskListActivity taskListActivity;
    private TaskHistoryActivity taskHistoryActivity;

    public static MainActivity instance;
    public static MainControls controls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper_activity);

        instance = this;

        // Создаем наши активити
        new DBHelper(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<>();

        View page = inflater.inflate(R.layout.activity_list_task, null);
        taskListActivity = new TaskListActivity(this, page);
        pages.add(page);

        page = inflater.inflate(R.layout.activity_history, null);
        taskHistoryActivity = new TaskHistoryActivity(this, page);
        pages.add(page);

        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        ViewPager pager = (ViewPager)findViewById(R.id.pages);

        pager.setAdapter(pagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        controls = new MainControls();

        ActivityFactory.InitActivity(this, controls);
        ActivityFactory.setListener(this, controls);

        anim_show = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_show);
        anim_hide = AnimationUtils.loadAnimation(getApplication(), R.anim.fab_hide);

        hideFabs();
    }

    static Animation anim_show;
    static Animation anim_hide;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("HHH", "->" + id);
        if (id == R.id.nav_import) {
            new YesNoDialog(this, new ImportFilesCommand(), "Выполнить инпорт из внешних файлов ?\nДанные в БД будут похерены !!!").show();
        } else if (id == R.id.nav_export) {
            new YesNoDialog(this, new ExportFilesCommand(), String.format("Выполнить экспорт БД во внешние файлы ?\n фалы попадут в папку '%s'", Settings.EXTERNAL_FILES_DIRECTORY)).show();
        } else if (id == R.id.nav_about) {
            new MessageDialog(this, "Эбаот", "Task dispatcher\nWriten for Seryoga Fomin\n\nBy Greg3D 07.11.2017");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        ViewHelper v = new ViewHelper(view);
        if(v.idEquals(controls.hide_Fab)){
            hideFabs();
        }
        else if(v.idEquals(controls.edit_Fab)){
            TaskEditActivity.state = State.EDIT;
            ActivitiesManager.startTaskEditActivity(this);
        }
        else if(v.idEquals(controls.add_Fab)){
            TaskEditActivity.state = State.ADD;
            ActivitiesManager.startTaskEditActivity(this);
        }
        else if(v.idEquals(controls.delete_Fab)){
            new YesNoDialog(this, new DeleteTaskCommand(), "Удаляем задачку ?").show();
        }

        taskListActivity.onClick(this, view);
        taskHistoryActivity.onClick(this, view);
    }

    public static void showFabs(){
//        controls.hide_Fab.startAnimation(AnimationUtils.loadAnimation(instance.getApplication(), R.anim.fab_show_300));
//        controls.add_Fab.startAnimation(AnimationUtils.loadAnimation(instance.getApplication(), R.anim.fab_show_200));
//        controls.edit_Fab.startAnimation(AnimationUtils.loadAnimation(instance.getApplication(), R.anim.fab_show_100));
//        controls.delete_Fab.startAnimation(AnimationUtils.loadAnimation(instance.getApplication(), R.anim.fab_show));
//
//        controls.hide_Fab.show();
//        controls.add_Fab.show();
//        controls.edit_Fab.show();
//        controls.delete_Fab.show();
//
//        controls.hide_Fab.setClickable(true);
//        controls.add_Fab.setClickable(true);
//        controls.edit_Fab.setClickable(true);
//        controls.delete_Fab.setClickable(true);

        controls.hide_Fab.show();
        controls.add_Fab.show();
        controls.edit_Fab.show();
        controls.delete_Fab.show();

    }

    public static void hideFabs(){
//        //controls.hide_Fab.startAnimation(AnimationUtils.loadAnimation(instance.getApplication(), R.anim.fab_hide_300));
//        controls.add_Fab.startAnimation(AnimationUtils.loadAnimation(instance.getApplication(), R.anim.fab_hide_200));
//        controls.edit_Fab.startAnimation(AnimationUtils.loadAnimation(instance.getApplication(), R.anim.fab_hide_100));
//        controls.delete_Fab.startAnimation(AnimationUtils.loadAnimation(instance.getApplication(), R.anim.fab_hide));
//
//        //controls.hide_Fab.setClickable(false);
//        controls.add_Fab.setClickable(false);
//        controls.edit_Fab.setClickable(false);
//        controls.delete_Fab.setClickable(false);

        controls.hide_Fab.hide();
        controls.add_Fab.hide();
        controls.edit_Fab.hide();
        controls.delete_Fab.hide();

//        controls.hide_Fab.hide();
//        controls.add_Fab.hide();
//        controls.edit_Fab.hide();
//        controls.delete_Fab.hide();
    }
}
