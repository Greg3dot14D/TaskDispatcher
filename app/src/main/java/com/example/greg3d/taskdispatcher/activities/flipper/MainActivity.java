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

import com.example.greg3d.taskdispatcher.R;
import com.example.greg3d.taskdispatcher.activities.flipper.adapters.SamplePagerAdapter;
import com.example.greg3d.taskdispatcher.activities.flipper.commands.ExportFilesCommand;
import com.example.greg3d.taskdispatcher.activities.flipper.commands.ImportFilesCommand;
import com.example.greg3d.taskdispatcher.activities.flipper.controls.HistoryControls;
import com.example.greg3d.taskdispatcher.activities.flipper.controls.MainControls;
import com.example.greg3d.taskdispatcher.activities.taskedit.TaskEditActivity;
import com.example.greg3d.taskdispatcher.activities.taskhistory.TaskHistoryActivity;
import com.example.greg3d.taskdispatcher.activities.taskhistory.commands.DeleteHistoryCommand;
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
    public static HistoryControls historyControls;

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
        historyControls = new HistoryControls();

        ActivityFactory.InitActivity(this, controls);
        ActivityFactory.setListener(this, controls);
        ActivityFactory.InitActivity(this, historyControls);
        ActivityFactory.setListener(this, historyControls);

        pager.addOnPageChangeListener(new OnChangePage(controls, historyControls));

        hideFabs();
        hideHistoryFabs();
        historyControls.closeControls();
        //TimerManager.startTimer(new SpentTimerTaskList());
    }

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
            if(controls.isShown)
                hideFabs();
            else
                showFabs();
        }
        if(v.idEquals(historyControls.hide_Fab)){
            if(historyControls.isShown)
                hideHistoryFabs();
            else
                showHistoryFabs();
        }
        else if(v.idEquals(controls.edit_Fab) && TaskListActivity.getInstance().isSelected()){
            TaskEditActivity.state = State.EDIT;
            ActivitiesManager.startTaskEditActivity(this);
        }
        else if(v.idEquals(historyControls.edit_Fab) && TaskHistoryActivity.getInstance().isSelected()){
            // TODO
            TaskEditActivity.state = State.EDIT_HISTORY;
            ActivitiesManager.startTaskEditActivity(this);
        }
        else if(v.idEquals(controls.add_Fab)){
            TaskEditActivity.state = State.ADD;
            ActivitiesManager.startTaskEditActivity(this);
        }
        else if(v.idEquals(controls.delete_Fab)){
            new YesNoDialog(this, new DeleteTaskCommand(), "Удаляем задачку ?").show();
        }
        else if(v.idEquals(historyControls.delete_Fab)){
            new YesNoDialog(this, new DeleteHistoryCommand(), "Удаляем запись из истории ?").show();
        }
        taskListActivity.onClick(this, view);
        taskHistoryActivity.onClick(this, view);
    }

    public static void showFabs(){
        if(!controls.isShown) {
            controls.showControls();
        }
        controls.setAnimationStartOffset(0);
        controls.isShown = true;
    }

    public static void showHistoryFabs(){
        if(!historyControls.isShown) {
            historyControls.showControls();
        }
        historyControls.setAnimationStartOffset(0);
        historyControls.isShown = true;
    }

    private void hideFabs(){
        if(controls.isShown) {
            controls.hideControls();
        }
        controls.setAnimationStartOffset(0);
        controls.isShown = false;
    }

    private void hideHistoryFabs(){
        if(historyControls.isShown) {
            historyControls.hideControls();
        }
        historyControls.setAnimationStartOffset(0);
        historyControls.isShown = false;
    }

    private static class OnChangePage implements ViewPager.OnPageChangeListener {
        private MainControls controls;
        private HistoryControls historyControls;

        public OnChangePage (MainControls controls, HistoryControls historyControls){
            this.controls = controls;
            this.historyControls = historyControls;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if(position == 1) {
                controls.setAnimationStartOffset(0);
                historyControls.setAnimationStartOffset(500);
                historyControls.openControls();
                controls.closeControls();
            }
            else if(position == 0) {
                historyControls.setAnimationStartOffset(0);
                controls.setAnimationStartOffset(500);
                controls.openControls();
                historyControls.closeControls();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
