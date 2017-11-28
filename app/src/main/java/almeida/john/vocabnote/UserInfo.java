package almeida.john.vocabnote;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by John on 02/11/2017.
 */




public class UserInfo {


    private String User_Auth_ID;
    private String User_Name;
    private String User_Picture;
   // private String WordList;

    private List<WordList> WordList;


    public UserInfo(String User_Auth_ID,String User_Name,String User_Picture,List<WordList> Wordlist  ) {

        this.User_Auth_ID = User_Auth_ID;
        this.User_Name = User_Name;
        this.User_Picture = User_Picture;
        this.WordList = Wordlist;

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

    public List<almeida.john.vocabnote.WordList> getWordList() {
        return WordList;
    }
}

