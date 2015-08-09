package com.sohyun.gradproj.randomalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends ActionBarActivity {
//    private static final String BASE_PATH = Environment.getExternalStorageDirectory() + "";
//    private static final String BASE_PATH = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_MUSIC ) + "";
//    private static final String NORMAL_PATH = BASE_PATH + "/media/MUSIC";
    private AlarmManager _am;
    private ListView AlarmList;
//    private MusicList list;
    private Button newAlarm, changeSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        list.updateSongList();

        _am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        newAlarm = (Button) findViewById(R.id.newAlarm);
        newAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallnewSettingActivity");
                Intent intentnewSettingActivity =
                        new Intent(MainActivity.this, Setting.class);
                startActivity(intentnewSettingActivity);
            }
        });
        changeSetting = (Button) findViewById(R.id.setting);
        changeSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallchangeSettingActivity");
                Intent intentchangeSettingActivity =
                        new Intent(MainActivity.this, Setting.class);
                startActivity(intentchangeSettingActivity);
            }
        });
    }

    //////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
