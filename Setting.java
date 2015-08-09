package com.sohyun.gradproj.randomalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.util.Calendar;

/**
 * Created by Keiden on 2015-08-10.
 */
public class Setting extends Activity{
    private static final String BASE_PATH = Environment.getExternalStorageDirectory() + "";
    private String filepath = BASE_PATH + "/media/MUSIC";
    private AlarmManager _am;
    private TimePicker mTime;

    private Button selMusic, OK;
    private ToggleButton _toggleSun, _toggleMon, _toggleTue, _toggleWed, _toggleThu, _toggleFri, _toggleSat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        _am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        _toggleSun = (ToggleButton) findViewById(R.id.toggle_sun);
        _toggleMon = (ToggleButton) findViewById(R.id.toggle_mon);
        _toggleTue = (ToggleButton) findViewById(R.id.toggle_tue);
        _toggleWed = (ToggleButton) findViewById(R.id.toggle_wed);
        _toggleThu = (ToggleButton) findViewById(R.id.toggle_thu);
        _toggleFri = (ToggleButton) findViewById(R.id.toggle_fri);
        _toggleSat = (ToggleButton) findViewById(R.id.toggle_sat);

        selMusic = (Button) findViewById(R.id.Musicsel);
        OK = (Button) findViewById(R.id.set);
        selMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallselMusicActivity");
                Intent intentselMusicActivity =
                        new Intent(Setting.this, MusicList.class);
                startActivity(intentselMusicActivity);
            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallsetActivity");
                onRegist(v);
                Intent intentsetActivity =
                        new Intent(Setting.this, MainActivity.class);
                startActivity(intentsetActivity);
            }
        });
    }

        public void onRegist(View v)
    {
        Log.i("Setting:onRegist", "|" + "========= regist" + "|");

        showRingtonePickerDialog();
        File file = new File(filepath);
        Log.i("MainActivity:onRegist", "| file exists? : " + file.exists() + "|" + file.hashCode());

        boolean[] week = { false, _toggleSun.isChecked(), _toggleMon.isChecked(), _toggleTue.isChecked(), _toggleWed.isChecked(),
                _toggleThu.isChecked(), _toggleFri.isChecked(), _toggleSat.isChecked() }; // sunday=1 이라서 0의 자리에는 아무 값이나 넣었음

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("file", file.toString());
        intent.putExtra("weekday", week);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, file.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + 10); // 10초 뒤

        long oneday = 24 * 60 * 60 * 1000;// 24시간

        // 10초 뒤에 시작해서 매일 같은 시간에 반복하기
//        _am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), oneday, pIntent);
    }

//    public void onUnregist(View v)
//    {
//        Log.i("MainActivity:onUnregist", "|" + "========= unregist" + "|");
//
//        File file = new File(NORMAL_PATH + "/drop_1235.m4a");
//        Log.i("MainActivity:onRegist", "| file exists? : " + file.exists() + "|" + file.hashCode());
//
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pIntent = PendingIntent.getBroadcast(this, file.hashCode(), intent, 0);
//
//        _am.cancel(pIntent);
//    }



    private void showRingtonePickerDialog() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select ringtone for notifications:");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_NOTIFICATION);
        startActivityForResult(intent, 777);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 999:
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    if (uri != null) {
                        String ringtonePath = uri.toString();
//                        Toast.makeText(getApplicationContext(), "ringtone=" + ringtonePath, Toast.LENGTH_LONG).show();
                        filepath = ringtonePath;
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
