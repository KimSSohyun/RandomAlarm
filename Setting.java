package com.sohyun.gradproj.randomalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

/**
 * Created by Keiden on 2015-08-10.
 */
public class Setting extends Activity{
    private Button selMusic, OK;
    private ToggleButton _toggleSun, _toggleMon, _toggleTue, _toggleWed, _toggleThu, _toggleFri, _toggleSat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

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
                Intent intentsetActivity =
                        new Intent(Setting.this, MainActivity.class);
                startActivity(intentsetActivity);
            }
        });
    }
}
