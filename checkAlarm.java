package com.sohyun.gradproj.randomalarm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Keiden on 2015-08-10.
 */
public class checkAlarm extends Activity{
    private Button endAlarm, reAlarm;
    private TextView thisTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        endAlarm = (Button) findViewById(R.id.quitAlarm);
        reAlarm = (Button) findViewById(R.id.reAlarm);
        endAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "endAlarm");
            }
        });
        reAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "reAlarm");
            }
        });

        thisTime = (TextView) findViewById(R.id.thisTime);
    }
}
