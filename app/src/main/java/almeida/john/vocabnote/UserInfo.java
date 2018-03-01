package almeida.john.vocabnote;


import java.util.List;

import almeida.john.vocabnote.almieda.john.fragments.Classification;

/**
 * Created by John on 02/11/2017.
 */




public class UserInfo {


    private String User_Auth_ID;
    private String User_Name;
    private String User_Picture;
   // private String WordList;

    private List<Classification> Classification;


    public UserInfo(String User_Auth_ID,String User_Name,String User_Picture,List<Classification> Classification  ) {

        this.User_Auth_ID = User_Auth_ID;
        this.User_Name = User_Name;
        this.User_Picture = User_Picture;
        this.Classification = Classification;

    }

    public String getUser_Auth_ID() {
        return User_Auth_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public String getUser_Picture() {
        return User_Picture;
    }

    public List<almeida.john.vocabnote.almieda.john.fragments.Classification> getClassification() {
        return Classification;
    }
}

