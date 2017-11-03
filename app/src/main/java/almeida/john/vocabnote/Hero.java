package almeida.john.vocabnote;



/**
 * Created by John on 02/11/2017.
 */

public class Hero {


    private String Name;
    private String Email;
    private String Gender;



    public Hero(String Name, String Email, String Gender) {
        this.Name = Name;
        this.Email = Email;
        this.Gender = Gender;

    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getGender() {
        return Gender;
    }
}
