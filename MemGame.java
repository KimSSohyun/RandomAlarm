package com.sohyun.androidproj;
//datamodel

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by KimSohyun on 2015-06-08.
 */
public class MemGame {
    public enum GameState{
        Running(0), NewQuiz(1), Over(2);
        private final int value;
        private GameState(int value){
            this.value = value;
        }
        public int value(){
            return value;
        }
    }

    public MemGame() throws Exception{
        level = 1;
        idx = 0;
        quiz = new ArrayList<Integer>();
        quizarrs = new int[level][maxButton];
        btnarr = new int[maxdy][maxdx];
        makeQuiz();
    }
    public MemGame(int l) throws Exception{
        level = l;
        idx = 0;
        quiz = new ArrayList<Integer>();
        quizarrs = new int[level][maxButton];
        btnarr = new int[maxdy][maxdx];
        makeQuiz();
    }

    private int level;
    private int idx;
    private int[][] quizarrs;
    private int[][] btnarr;
    public ArrayList<Integer> quiz;
    private final int maxdx = 4, maxdy = 4;
    private final int maxButton = maxdx*maxdy;

    private void makeQuiz(){
        int i, rand;
        Random random = new Random();
        quizarrs = new int[level][maxButton];
        for(i = 0; i<level; i++){
            rand = random.nextInt(maxButton);
            Log.d("MemGame", "rand - " + (rand+1));
            quiz.add(rand);
            quizarrs[i][rand] = 1;
        }
    }

    public int[][] get_array(int num){
        btnarr = FirstToSecondArr(quizarrs[num]);
        return btnarr;
    }
    public int[][] FirstToSecondArr(int[] array){
        int i, j;
        for(i=0; i<maxdy; i++){
            for(j=0; j<maxdx; j++){
                btnarr[i][j] = array[i*maxdy+j];
            }
        }
        return btnarr;
    }

    public GameState accept(int key) throws Exception{
        GameState state = GameState.Running;
//        char quizkey = NumtoAscii(quiz.get(idx));
        int quizkey = quiz.get(idx);
        Log.d("MemGame", "quiz key:"+quizkey+", accept key:"+key);
        if(key == quizkey) {
            idx++;
            if(idx == level) {
                idx = 0;
                level++;
                makeQuiz();
                return GameState.NewQuiz;
            }
            return GameState.Running;
        }
        else if(key == -1){ return state; }
        else {
            idx = 0;
            return GameState.Over;
        }
    }

    private char NumtoAscii(int num){
        if(num < 10) return (char)('0' + num);
        else return (char)('a'+ num - 10);
    }
}
