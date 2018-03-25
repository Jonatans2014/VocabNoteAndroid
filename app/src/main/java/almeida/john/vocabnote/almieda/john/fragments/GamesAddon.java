package almeida.john.vocabnote.almieda.john.fragments;

/**
 * Created by John on 22/03/2018.
 */

public class GamesAddon {

    private int life;
    private   int points;
    private  static int overalpoints;
    private int sucess;
    private int failure;



    GamesAddon(int life, int points, int sucess, int failure)
    {
        this.life= life ;
        this.points = points;
        this.failure = failure;
        this.sucess = sucess;
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


    public int removeLife()
    {
        life--;

        if(life == 0)
        {
            System.out.println("Game Over!");
        }

        return life;
    }

    public int addPoints()
    {
        points += 25;

        return points;
    }
}



