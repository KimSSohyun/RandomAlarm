package com.sohyun.androidproj;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        Error(-1), Init(0), Show(1),  Running(2), Paused(3);
        private final int value;
        private UIState(int value){ this.value = value; }
        public int value(){ return value; }
        public static UIState fromInteger(int value){
            switch(value){
                case -1: return Error;
                case 0: return Init;
                case 1: return Show;
                case 2: return Running;
                case 3: return Paused;
            }
            return null;
        }
    }
    int[][] stateMatrix = {
            {-1, 1, -1, -1, -1},
            {0, -1, 2, 1, 1},
            {0, 1, 2, -1, 1},
            {0, 1, -1, 1, 1},
    };
    boolean[][] buttonState = {
            {true, false, false},
            {true, true, false},
            {true, true, true},
            {false, true, false},
    };

    private MemView myView, peerView;
    private Button startBtn, pauseBtn;
    private Button Btn1, Btn2, Btn3, Btn4, Btn5, Btn6, Btn7, Btn8, Btn9, Btn10, Btn11,
            Btn12, Btn13, Btn14, Btn15, Btn16;
    private MemGame myGame, peerGame;
    private int myKey, peerKey;
    private UIState uiState = UIState.Init;
    private MainActivity thisObject;
    private Handler mainHandler;
    private int level;

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

        mainHandler = new Handler();
        thisObject = MainActivity.this;
        setButtonsState();
        level = 1;
    }
    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        public void onClick(View v){
            int id = v.getId();
            myKey = -1;
            UICmd uiCmd = UICmd.Finish;
            switch (id) {
                case R.id.startBtn:
                    if(uiState == uiState.Init) uiCmd = UICmd.Start;
                    else if (uiState == UIState.Running) {
                        executeUICmd(UICmd.Finish);
                        return;
                    }
                    else Log.e("MainActivity", "uiState error!");
                    Log.d("MainActivity", "startBtn pressed");
                    break;
                case R.id.pauseBtn:
                    if(uiState == UIState.Running || uiState == UIState.Show) 
                        uiCmd = UICmd.Pause;
                    else if(uiState == UIState.Paused) uiCmd = UICmd.Resume;
                    else Log.e("MainActivity", "uiState error!");
                    break;
                case R.id.btn1:
//                    Btn1.setBackgroundColor(0xFF000000);
                    myKey = 0;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn2:
//                    Btn2.setBackgroundColor(0xFF000000);
                    myKey = 1;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn3:
//                    Btn3.setBackgroundColor(0xFF000000);
                    myKey = 2;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn4:
//                    Btn4.setBackgroundColor(0xFF000000);
                    myKey = 3;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn5:
//                    Btn5.setBackgroundColor(0xFF000000);
                    myKey = 4;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn6:
//                    Btn6.setBackgroundColor(0xFF000000);
                    myKey = 5;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn7:
//                    Btn7.setBackgroundColor(0xFF000000);
                    myKey = 6;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn8:
//                    Btn8.setBackgroundColor(0xFF000000);
                    myKey = 7;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn9:
//                    Btn9.setBackgroundColor(0xFF000000);
                    myKey = 8;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn10:
//                    Btn10.setBackgroundColor(0xFF000000);
                    myKey = 9;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn11:
//                    Btn11.setBackgroundColor(0xFF000000);
                    myKey = 10;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn12:
//                    Btn12.setBackgroundColor(0xFF000000);
                    myKey = 11;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn13:
//                    Btn13.setBackgroundColor(0xFF000000);
                    myKey = 12;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn14:
//                    Btn14.setBackgroundColor(0xFF000000);
                    myKey = 13;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn15:
//                    Btn15.setBackgroundColor(0xFF000000);
                    myKey = 14;
                    uiCmd = UICmd.Update;
                    break;
                case R.id.btn16:
//                    Btn16.setBackgroundColor(0xFF000000);
                    myKey = 15;
                    uiCmd = UICmd.Update;
                    break;
                default:
                    break;
            }
            peerKey = myKey;
            myView.printClickBtn(myKey);
            executeUICmd(uiCmd);
        }
    };

//    public void clearButtonColor(){
//        Btn1.setBackgroundColor(0xffffffff);
//        Btn2.setBackgroundColor(0xffffffff);
//        Btn3.setBackgroundColor(0xffffffff);
//        Btn4.setBackgroundColor(0xffffffff);
//        Btn5.setBackgroundColor(0xffffffff);
//        Btn6.setBackgroundColor(0xffffffff);
//        Btn7.setBackgroundColor(0xffffffff);
//        Btn8.setBackgroundColor(0xffffffff);
//        Btn9.setBackgroundColor(0xffffffff);
//        Btn10.setBackgroundColor(0xffffffff);
//        Btn11.setBackgroundColor(0xffffffff);
//        Btn12.setBackgroundColor(0xffffffff);
//        Btn13.setBackgroundColor(0xffffffff);
//        Btn14.setBackgroundColor(0xffffffff);
//        Btn15.setBackgroundColor(0xffffffff);
//        Btn16.setBackgroundColor(0xffffffff);
//    }

    private void executeUICmd(UICmd c){
        Log.d("MainActivity", "(uiState, uiCmd)=(" + uiState.value() + "," + c.value() + ")");
        if(uiState == UIState.Init && c == UICmd.Finish){
            Log.d("MainActivity", "ignored double Finish due to EchoServer");
            return;
        }
        uiState = UIState.fromInteger(stateMatrix[uiState.value()][c.value()]);
        if(uiState == UIState.Error) Log.e("MainActivity", "uiState error!");
        switch (c.value()){
            case 0: mainHandler.post(finishRunnable); break;
            case 1: mainHandler.post(startRunnable); break;
            case 2: mainHandler.post(pauseRunnable); break;
            case 3: mainHandler.post(resumeRunnable); break;
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
            level = 1;
            Toast.makeText(thisObject, "Game Over", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable startRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                Log.d("MainActivity", "newly created!");
                Toast.makeText(thisObject, "Level"+level, Toast.LENGTH_SHORT);
                myView.start(level);
                myView.printQuiz(level);
//                peerView.start(level);
//                peerView.printQuiz(level);
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
            myView.deactivateTimer();
            thisObject.startBtn.setText("QUIT");
            thisObject.pauseBtn.setText("RESUME");
            Toast.makeText(thisObject, "Game Paused", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable resumeRunnable = new Runnable() {
        @Override
        public void run() {
            setButtonsState();
            myView.activateTimer();
            thisObject.startBtn.setText("QUIT");
            thisObject.pauseBtn.setText("PAUSE");
            Toast.makeText(thisObject, "Game Resumed", Toast.LENGTH_SHORT).show();
        }
    };
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            try{
                MemGame.GameState state = myView.accept(myKey);

                if(state == MemGame.GameState.NewQuiz){
                    level++;
                    executeUICmd(UICmd.Start);
                }
                else if(state == MemGame.GameState.Over)
                    executeUICmd(UICmd.Finish);
                myView.invalidate();
//                peerView.invalidate();
            } catch(Exception e) { e.printStackTrace(); }
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
