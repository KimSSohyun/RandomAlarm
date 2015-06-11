package com.sohyun.androidproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    enum UICmd{
        Finish(0), Start(1), Pause(2), Resume(3), Update(4);
        private final int value;
        private UICmd(int value){
            this.value = value;
        }
        public int value(){
            return value;
        }
    }
    enum UIState{
        Error(-1), Init(0), Running(1), Paused(2);
        private final int value;
        private UIState(int value){ this.value = value; }
        public int value(){ return value; }
        public static UIState fromInteger(int value){
            switch(value){
                case -1: return Error;
                case 0: return Init;
                case 1: return Running;
                case 2: return Paused;
            }
            return null;
        }
    }
    int[][] stateMatrix = {
            {-1, 1, -1, -1, -1},
            {0, 1, 2, -1, 1},
            {0, 1, -1, 1, 1},
    };
    boolean[][] buttonState = {
            {true, false, false},
            {true, true, true},
            {false, true, false},
    };

    private MemView myView, peerView;
    private Button startBtn, pauseBtn;
    private Button Btn1, Btn2, Btn3, Btn4, Btn5, Btn6, Btn7, Btn8, Btn9, Btn10, Btn11,
            Btn12, Btn13, Btn14, Btn15, Btn16;
    private int myKey, peerKey;
    private UIState uiState = UIState.Init;
    private MainActivity thisObject;
    private Handler mainHandler, TimerHandler;
    private int level;
    private TextView levelText, TimeCounter;
    private Timer timer;
    private int sec, minisec;

    private final int getNetConfigInfo = 0;
    private String serverHostName = "220.149.85.227";
    private int serverPort = 9866;
//    private EchoServer echoServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        levelText = (TextView)findViewById(R.id.levelText);
        TimeCounter = (TextView)findViewById(R.id.TimeCounter);
        myView = (MemView) findViewById(R.id.myView);
        peerView = (MemView) findViewById(R.id.peerView);
        startBtn = (Button) findViewById(R.id.startBtn);
        pauseBtn = (Button) findViewById(R.id.pauseBtn);
        Btn1 = (Button) findViewById(R.id.btn1);
        Btn2 = (Button) findViewById(R.id.btn2);
        Btn3 = (Button) findViewById(R.id.btn3);
        Btn4 = (Button) findViewById(R.id.btn4);
        Btn5 = (Button) findViewById(R.id.btn5);
        Btn6 = (Button) findViewById(R.id.btn6);
        Btn7 = (Button) findViewById(R.id.btn7);
        Btn8 = (Button) findViewById(R.id.btn8);
        Btn9 = (Button) findViewById(R.id.btn9);
        Btn10 = (Button) findViewById(R.id.btn10);
        Btn11 = (Button) findViewById(R.id.btn11);
        Btn12 = (Button) findViewById(R.id.btn12);
        Btn13 = (Button) findViewById(R.id.btn13);
        Btn14 = (Button) findViewById(R.id.btn14);
        Btn15 = (Button) findViewById(R.id.btn15);
        Btn16 = (Button) findViewById(R.id.btn16);

        startBtn.setOnClickListener(OnClickListener);
        pauseBtn.setOnClickListener(OnClickListener);
        Btn1.setOnClickListener(OnClickListener);
        Btn2.setOnClickListener(OnClickListener);
        Btn3.setOnClickListener(OnClickListener);
        Btn4.setOnClickListener(OnClickListener);
        Btn5.setOnClickListener(OnClickListener);
        Btn6.setOnClickListener(OnClickListener);
        Btn7.setOnClickListener(OnClickListener);
        Btn8.setOnClickListener(OnClickListener);
        Btn9.setOnClickListener(OnClickListener);
        Btn10.setOnClickListener(OnClickListener);
        Btn11.setOnClickListener(OnClickListener);
        Btn12.setOnClickListener(OnClickListener);
        Btn13.setOnClickListener(OnClickListener);
        Btn14.setOnClickListener(OnClickListener);
        Btn15.setOnClickListener(OnClickListener);
        Btn16.setOnClickListener(OnClickListener);

        mainHandler = new Handler();
        TimerHandler = new Handler();
        thisObject = MainActivity.this;
        setButtonsState();
        level = 1;
//        echoServer = new EchoServer(mainHandler2, thisObject);
    }
    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        public void onClick(View v){
            int id = v.getId();
            myKey = -1;
            UICmd uiCmd = UICmd.Finish;
            switch (id) {
                case R.id.startBtn:
                    if(uiState == uiState.Init) {
                        uiCmd = UICmd.Start;
                        sec = 60; minisec = 0;
                    }
                    else if (uiState == UIState.Running) {
                        executeUICmd(UICmd.Finish);
                        return;
                    }
                    else Log.e("MainActivity", "uiState error!");
                    Log.d("MainActivity", "startBtn pressed");
                    break;
                case R.id.pauseBtn:
                    if(uiState == UIState.Running) uiCmd = UICmd.Pause;
                    else if(uiState == UIState.Paused) uiCmd = UICmd.Resume;
                    else Log.e("MainActivity", "uiState error!");
                    break;
                case R.id.btn1: myKey = 0; uiCmd = UICmd.Update; break;
                case R.id.btn2: myKey = 1; uiCmd = UICmd.Update; break;
                case R.id.btn3: myKey = 2; uiCmd = UICmd.Update; break;
                case R.id.btn4: myKey = 3; uiCmd = UICmd.Update; break;
                case R.id.btn5: myKey = 4; uiCmd = UICmd.Update; break;
                case R.id.btn6: myKey = 5; uiCmd = UICmd.Update; break;
                case R.id.btn7: myKey = 6; uiCmd = UICmd.Update; break;
                case R.id.btn8: myKey = 7; uiCmd = UICmd.Update; break;
                case R.id.btn9: myKey = 8; uiCmd = UICmd.Update; break;
                case R.id.btn10: myKey = 9; uiCmd = UICmd.Update; break;
                case R.id.btn11: myKey = 10; uiCmd = UICmd.Update; break;
                case R.id.btn12: myKey = 11; uiCmd = UICmd.Update; break;
                case R.id.btn13: myKey = 12; uiCmd = UICmd.Update; break;
                case R.id.btn14: myKey = 13; uiCmd = UICmd.Update; break;
                case R.id.btn15: myKey = 14; uiCmd = UICmd.Update; break;
                case R.id.btn16: myKey = 15; uiCmd = UICmd.Update; break;
                default: break;
            }
            peerKey = myKey;
            myView.printClickBtn(myKey);
            executeUICmd(uiCmd);
        }
    };

    private void executeUICmd(UICmd c){
        Log.d("MainActivity", "(uiState, uiCmd)=(" + uiState.value() + "," + c.value() + ")");
        if(uiState == UIState.Init && c == UICmd.Finish){
            Log.d("MainActivity", "ignored double Finish due to EchoServer");
            return;
        }
        uiState = UIState.fromInteger(stateMatrix[uiState.value()][c.value()]);
        if(uiState == UIState.Error) {
            Log.e("MainActivity", "uiState error!");
            uiState = UIState.Init;
            return;
        }
        switch (c.value()){
            case 0: deactivateTimer(); mainHandler.post(finishRunnable); break;
            case 1: activateTimer(); mainHandler.post(startRunnable); break;
            case 2: deactivateTimer(); mainHandler.post(pauseRunnable); break;
            case 3: activateTimer(); mainHandler.post(resumeRunnable); break;
            case 4: mainHandler.post(updateRunnable); break;
        }
    }
    private Runnable finishRunnable = new Runnable() {
        @Override
        public void run() {
//            echoServer.disconnect();
            setButtonsState();
            thisObject.startBtn.setText("START");
            thisObject.pauseBtn.setText("PAUSE");
            level = 1;
            deactivateTimer();
            Toast.makeText(thisObject, "Game Over", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable startRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                Log.d("MainActivity", "newly created!");
                levelText.setText("Level" + level);
                myView.start(level);
                myView.printQuiz(level);
                peerView.start(level);
                peerView.newGame = myView.newGame;
                peerView.printQuiz(level);
//                echoServer.connect(serverHostName, serverPort);
            } catch (Exception e) { e.printStackTrace(); }
            setButtonsState();
            thisObject.startBtn.setText("QUIT");
            thisObject.pauseBtn.setText("PAUSE");
        }
    };
    private Runnable pauseRunnable = new Runnable() {
        @Override
        public void run() {
            setButtonsState();
            myView.deactivateTimer(); peerView.deactivateTimer();
            thisObject.startBtn.setText("QUIT");
            thisObject.pauseBtn.setText("RESUME");
            Toast.makeText(thisObject, "Game Paused", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable resumeRunnable = new Runnable() {
        @Override
        public void run() {
            setButtonsState();
            myView.activateTimer(); peerView.activateTimer();
            thisObject.startBtn.setText("QUIT");
            thisObject.pauseBtn.setText("PAUSE");
            Toast.makeText(thisObject, "Game Resumed", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                int ch = myKey;
                MemGame.GameState state = myView.accept(ch);
//                echoServer.send(ch);

                if(state == MemGame.GameState.NewQuiz){
                    level++;
                    executeUICmd(UICmd.Start);
                }
                else if(state == MemGame.GameState.Over) {
                    myView.printWrong();
                    executeUICmd(UICmd.Start);
                }
                myView.invalidate();
                peerView.invalidate();
            } catch(Exception e) { e.printStackTrace(); }
        }
    };

    private Runnable UpdateTimerHandler = new Runnable() {
        @Override
        public void run() {
            TimeCounter.setText(sec + ":0" + minisec);

            if((minisec == 0) && (sec == 0)) executeUICmd(UICmd.Finish);
            if(minisec == 0) { minisec = 10; sec--; }
            minisec --;
        }
    };
    private class TimerTaskHandler extends TimerTask {
        public void run(){
            TimerHandler.post(UpdateTimerHandler);
        }
    }
    private void activateTimer(){
        if(timer == null){
            TimerTaskHandler job = new TimerTaskHandler();
            timer = new Timer(true);
            timer.scheduleAtFixedRate(job, 100, 100);
        }
    }
    private void deactivateTimer(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
//    private Handler mainHandler2 = new Handler(){
//        public void handleMessage(Message msg){
//            switch(msg.what){
//                case -1:
//                    Toast.makeText(thisObject, "Socket aborted", Toast.LENGTH_SHORT).show();
//                    Log.d("MainActivity", "Socket aborted");
//                    break;
//                case 1:
//                    Toast.makeText(thisObject, "Socket connected", Toast.LENGTH_SHORT).show();
//                    Log.d("MainActivity", "Socket connected");
//                    break;
//                case 2:
//                    peerKey = (int)('0' - msg.arg1);
//                    Log.d("MainActivity", "peerKey='"+peerKey+"'");
//                    if(msg.arg1 == 0){
//                        Toast.makeText(thisObject, "Socket available", Toast.LENGTH_SHORT).show();
//                        Log.d("MainActivity", "Socket available");
//                    }
//                    else{
//                        peerKey = (int)('0' - msg.arg1);
//                        Log.d("MainActivity", "peerKey='"+peerKey+"'");
//                            try{
//                            MemGame.GameState state = peerView.accept(peerKey);
//                                peerView.printClickBtn(peerKey);
//                            if(state == MemGame.GameState.Over){
//                                peerView.printWrong();
//                                executeUICmd(UICmd.Start);
//                            }
//                        } catch(Exception e){
//                            e.printStackTrace();
//                        }
//                        peerView.invalidate();
//                    }
//                    break;
//                case 3:
//                    Toast.makeText(thisObject, "Socket disconnected", Toast.LENGTH_SHORT).show();
//                    Log.d("MainActivity", "Socket disconnected");
//                    break;
//                case 4:
//                    Toast.makeText(thisObject, "Socket unavailable", Toast.LENGTH_SHORT).show();
//                    Log.d("MainActivity", "Socket unavailable");
//                    break;
//                default:
//                    Toast.makeText(thisObject, "mainHandler2 error", Toast.LENGTH_SHORT).show();
//                    Log.d("MainActivity", "mainHandler2 error");
//                    break;
//            }
//        };
//    };


    private void setButtonsState(){
        if(uiState == UIState.Error) return;
        boolean flagStart = buttonState[uiState.value()][0];
        boolean flagPause = buttonState[uiState.value()][1];
        boolean flagOther = buttonState[uiState.value()][2];
        startBtn.setEnabled(flagStart);
        pauseBtn.setEnabled(flagPause);
        Btn1.setEnabled(flagOther);
        Btn2.setEnabled(flagOther);
        Btn3.setEnabled(flagOther);
        Btn4.setEnabled(flagOther);
        Btn5.setEnabled(flagOther);
        Btn6.setEnabled(flagOther);
        Btn7.setEnabled(flagOther);
        Btn8.setEnabled(flagOther);
        Btn9.setEnabled(flagOther);
        Btn10.setEnabled(flagOther);
        Btn11.setEnabled(flagOther);
        Btn12.setEnabled(flagOther);
        Btn13.setEnabled(flagOther);
        Btn14.setEnabled(flagOther);
        Btn15.setEnabled(flagOther);
        Btn16.setEnabled(flagOther);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(uiState == UIState.Running)
            executeUICmd(UICmd.Pause);
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(uiState == UIState.Paused)
            executeUICmd(UICmd.Resume);
    }
    @Override
     protected void onDestroy(){
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
//        echoServer.disconnect();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case getNetConfigInfo:
                if(resultCode == RESULT_OK){
                    serverHostName = data.getStringExtra("serverHostName");
                    String s = data.getStringExtra("serverPort");
                    serverPort = Integer.parseInt(s);
                }
                else if(resultCode == RESULT_CANCELED){
                    Log.d("MainActivity", "NetConfigActivity canceled");
                }
                break;
        }
    }

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
