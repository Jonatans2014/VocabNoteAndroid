package almeida.john.vocabnote.almieda.john.fragments;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by John on 22/03/2018.
 */

public class GamesAddon {

    private int life;
    private  int points;


    private  static int HighestScore;
    private static  int overAllScore;
    private  static  int overAllHighestScore;
    private  static  int overallCorrect;
    private  static  int overallIncorret;

    private int correct;
    private int incorrect;
    private int sucess;
    private int failure;
    private  int addOrRemovepoints;

    private int lvl2;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int getTimer;
    LinkedList<Integer> ListUserTimeGuessingWord = new LinkedList<Integer>();
    Handler handler;

    public LinkedList<Integer> getListUserTimeGuessingWord() {
        return ListUserTimeGuessingWord;
    }



    int Seconds, Minutes, MilliSeconds ;


    GamesAddon()
    {

        handler = new Handler() ;
        this.life= 3 ;
        this.points = 0;
        correct = 0;
        incorrect = 0;
        getTimer = 0;
        overAllScore = 0;
        lvl2 = 0;
        addOrRemovepoints = 50;
    }


    public static int getOverAllHighestScore() {
        return overAllHighestScore;
    }

    public static void setOverAllHighestScore(int overAllHighestScore) {
        GamesAddon.overAllHighestScore = overAllHighestScore;
    }

    public int getHighestScore() {
        return HighestScore;
    }

    public void setHighestScore(int highestScore) {
        HighestScore = highestScore;
    }

    public static int getOverallCorrect() {
        return overallCorrect;
    }

    public static void setOverallCorrect(int overallCorrect) {
        GamesAddon.overallCorrect += overallCorrect;
    }

    public static int getOverallIncorret() {
        return overallIncorret;
    }

    public static void setOverallIncorret(int overallIncorret) {
        GamesAddon.overallIncorret += overallIncorret;
    }

    public int getLvl2() {
        return lvl2;
    }

    public static int getOverAllScore() {
        return overAllScore;
    }

    public static void setOverAllScore(int overAllScore) {
        GamesAddon.overAllScore += overAllScore;
    }


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getSucess() {
        return sucess;
    }

    public void setSucess(int sucess) {
        this.sucess = sucess;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }


    public int increaseIncorrect()
    {
        incorrect ++;

        return incorrect;
    }
    public int increaseCorrect()
    {
        correct ++;

        return correct;
    }


    public void startTimer()
    {


        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
    }


    public void addTimertoLinkedListAndReset()
    {
        TimeBuff += MillisecondTime;

        handler.removeCallbacks(runnable);


        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;

        //ListUserTimeGuessingWord.add(setTimer.getText().toString());


       ListUserTimeGuessingWord.addFirst(getTimer);

       getTimer = 0;


        System.out.println("timer "+ ListUserTimeGuessingWord.getFirst());
       // setTimer.setText("00:00");
    }



    public Runnable runnable = new Runnable() {


        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            //Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            getTimer = Seconds;
            handler.postDelayed(this, 0);
        }

    };



    public int removeLife()
    {
        life--;

        return life;
    }




    public int addLife()
    {
        life++;


        return life;
    }

    public int addPoints( )
    {

        points += addOrRemovepoints;
        return points;
    }

    public  int removePoints()
    {
        points -= addOrRemovepoints;
        return  points;
    }

}



