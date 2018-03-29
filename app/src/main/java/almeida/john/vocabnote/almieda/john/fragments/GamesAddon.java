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
    private   int points;
    private int help1;
    private int help2;
    private int help3;

    private  static int overalpoints;
    private int sucess;
    private int failure;


    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int getTimer;
    LinkedList<Integer> ListUserTimeGuessingWord = new LinkedList<Integer>();
    Handler handler;

    public static int getOveralpoints() {
        return overalpoints;
    }

    public long getMillisecondTime() {
        return MillisecondTime;
    }

    public long getStartTime() {
        return StartTime;
    }



    public int getGetTimer() {
        return getTimer;
    }

    public LinkedList<Integer> getListUserTimeGuessingWord() {
        return ListUserTimeGuessingWord;
    }



    int Seconds, Minutes, MilliSeconds ;




    GamesAddon(int life, int points, int sucess, int failure)
    {

        handler = new Handler() ;
        this.life= life ;
        this.points = points;
        this.failure = failure;
        this.sucess = sucess;


        getTimer = 0;
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


    public void startTimer()
    {


        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
    }

    public void DeductPointsAccordingtoHelp(String getHelpString)
    {
        if(getHelpString.equals("help1"))
        {
            points -= 50;
        }
        else if(getHelpString.equals("help2"))
        {
            points -= 25;

        }
        else
        {
            points -= 12;
        }
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

//            getTimer = "" + Minutes + ":"
//                    + String.format("%02d", Seconds) + ":"
//                    + String.format("%03d", MilliSeconds;

//            setTimer.setText("" + Minutes + ":"
//                   + String.format("%02d", Seconds) + ":"
//                   + String.format("%03d", MilliSeconds));
            handler.postDelayed(this, 0);
        }

    };



    public int removeLife()
    {
        life--;

        if(life == 0)
        {
            System.out.println("Game Over!");
        }

        return life;
    }




    public int addLife()
    {
        life++;


        return life;
    }

    public int addPoints(String getHelpString)
    {

        if(getHelpString.equals("none"))
        {
            points += 100;
        }
        else if(getHelpString.equals("help1"))
        {
            points += 50;
        }
        else if(getHelpString.equals("help2"))
        {
            points += 25;
        }
        else if(getHelpString.equals("help3"))
        {
            points += 12;
        }

        return points;
    }
    //setTimer
    public  void setTimer(final TextView timer)
    {
        String getdone;
        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(""+String.format("%d:%d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timer.setText("0");
            }
        }.start();

    }
}



