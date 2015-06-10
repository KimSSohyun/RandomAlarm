package com.sohyun.androidproj;
//datamodel

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
        quizarray1 = new int[16];
        quizarray2 = new int[4][4];
        makeQuiz();
    }
    public MemGame(int l) throws Exception{
        level = l;
        idx = 0;
        quiz = new ArrayList<Integer>();
        makeQuiz();
    }

    private int level;
    private int idx;
    private int[] quizarray1;
    private int[][] quizarray2;
    public ArrayList<Integer> quiz;
    private final int BtnNum = 16;

    private void makeQuiz(){
        int i, rand;
        Random random = new Random();
        for(i = 0; i<level; i++){
            rand = random.nextInt(BtnNum);
            quiz.add(rand);
            quizarray1[rand] = 1;
        }
    }

    public int[][] get_array(){
        quizarray2 = FirstToSecondArr(quizarray1);
        return quizarray2;
    }
    private int[][] FirstToSecondArr(int[] array){
        int i, j;
        for(i=0; i<4; i++){
            for(j=0; j<4; j++){
                quizarray2[i][j] = array[i*4+j];
            }
        }
        return quizarray2;
    }

    public GameState accept(char key) throws Exception{
        GameState state = GameState.Running;
        char quizkey = NumtoAscii(quiz.get(idx));
        if(key == quizkey) {
            idx++;
            if(idx == level) return GameState.NewQuiz;
            return GameState.Running;
        }
        else {
            idx = 0;
            return GameState.Over;
        }
        /*switch (key) {
            case '0':
                break;
            case '1':
                break;
            case '2':
                break;
            case '3':
                break;
            case '4':
                break;
            case '5':
                break;
            case '6':
                break;
            case '7':
                break;
            case '8':
                break;
            case '9':
                break;
            case 'a':
                break;
            case 'b':
                break;
            case 'c':
                break;
            case 'd':
                break;
            case 'e':
                break;
            case 'f':
                break;
        }
        return state;*/
    }

    public char NumtoAscii(int num){
        if(num < 10) return (char) num;
        else return (char)('a'+ num - 10);
    }
}
