package com.sohyun.androidproj;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;

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
            {0, -1, 2, -1, 1},
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
    private MemGame myGame, peerGame;
    private LinearLayout linearLayout;
    private char myKey, peerKey;
    private UIState uiState = UIState.Init;
    private Timer timer;
    private MainActivity thisObject;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
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

        Btn16.setBackgroundColor( 0xFF000000 );
        linearLayout = ( LinearLayout )findViewById( R.id.linearLayout );

        mainHandler = new Handler();
        //setButtonsState();
        thisObject = MainActivity.this;
    }
    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        public void onClick(View v){
            int id = v.getId();
            switch (id) {
                case R.id.startBtn:
                    Toast.makeText(MainActivity.this, "Game Starts",
                            Toast.LENGTH_SHORT).show();
                    try {
                        myView.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    myView.invalidate();
                    Log.d("MainActivity", "startBtn pressed");
                    break;
                case R.id.pauseBtn:
                    break;
                default:
                    break;
            }
        }
    };


/*    @Override
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

    private class TimerHandler extends TimerTask {
        public void run(){
            executeUICmd(UICmd.Update);
        }
    }
    private void activateTimer(){
        if(timer == null){
            TimerHandler job = new TimerHandler();
            timer = new Timer(true);
            timer.scheduleAtFixedRate(job, 1000, 1000);
        }
    }
    private void deactivateTimer(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }
    private void executeUICmd(UICmd c){
        Log.d("MainActivity", "(uiState, uiCmd)=(" + uiState.value() + "," + c.value() + ")");
        if(uiState == UIState.Init && c == UICmd.Finish){
            Log.d("MainActivity", "ignored double Finish due to EchoServer");
            return;
        }
        uiState = UIState.fromInteger(stateMatrix[uiState.value()][c.value()]);
        if(uiState == UIState.Error) Log.e("MainActivity", "uiState error!");
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
            //echoServer.disconnect();
            setButtonsState();
            thisObject.startBtn.setText("START");
            thisObject.pauseBtn.setText("PAUSE");
            Toast.makeText(thisObject, "Game Over", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable startRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                Log.d("MainActivity", "newly created!");
                myView.start();
                myView.invalidate();
            } catch (Exception e) { e.printStackTrace(); }
            setButtonsState();
            thisObject.startBtn.setText("QUIT");
            thisObject.pauseBtn.setText("PAUSE");
            //setContentView(R.layout.activity_touch);
            Toast.makeText(thisObject, "Game Started", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable pauseRunnable = new Runnable() {
        @Override
        public void run() {
            setButtonsState();
            thisObject.startBtn.setText("QUIT");
            thisObject.pauseBtn.setText("RESUME");
            Toast.makeText(thisObject, "Game Paused", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable resumeRunnable = new Runnable() {
        @Override
        public void run() {
            setButtonsState();
            thisObject.startBtn.setText("QUIT");
            thisObject.pauseBtn.setText("PAUSE");
            Toast.makeText(thisObject, "Game Resumed", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                char ch = myKey;
                MemGame.GameState state = myView.accept(ch);
                if(state == MemGame.GameState.NewQuiz){
                    //state = myView.accept(currBlkType);
                    if(state == MemGame.GameState.Over)
                        executeUICmd(UICmd.Finish);
                }
                myView.invalidate();
                peerView.invalidate();
            } catch(Exception e) { e.printStackTrace(); }
        }
    };

    private View.OnClickListener OnClickListener = new View.OnClickListener(){
        public void onClick(View v){
            int id = v.getId();
            UICmd uiCmd = UICmd.Finish;
            switch (id){
                case R.id.startBtn:
                    if(uiState == uiState.Init) uiCmd = UICmd.Start;
                    else if (uiState == UIState.Running) {
                        executeUICmd(UICmd.Pause);
                        return;
                    }
                    else Log.e("MainActivity", "uiState error!");
                    break;
                case R.id.pauseBtn:
                    if(uiState == UIState.Running) uiCmd = UICmd.Pause;
                    else if(uiState == UIState.Paused) uiCmd = UICmd.Resume;
                    else Log.e("MainActivity", "uiState error!");
                    break;
                default:
                    return;
            }
            executeUICmd(uiCmd);
        }
    };

    private void setButtonsState(){
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
    }*/

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
