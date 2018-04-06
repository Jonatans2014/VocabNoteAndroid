package almeida.john.vocabnote.almieda.john.fragments;

import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.service.carrier.CarrierService;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import almeida.john.vocabnote.MainActivity;
import almeida.john.vocabnote.R;

public class SynonymGameActivity extends AppCompatActivity  {


    TextView mainWord, text1, text2, text3;
    int intValue;
    String gamefrag =  "gamesfrag";
    String chatbot  = "chatbotfrag";
    String getStringFromFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonym_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        text1 = (TextView) findViewById(R.id.play);
//        mainWord = (TextView) findViewById(R.id.word2);
//        text2 = (TextView) findViewById(R.id.word3);
//        text3 = (TextView) findViewById(R.id.word1);
//
//
//        text1.setOnClickListener(this);





        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("position", 0);
        getStringFromFragment =  mIntent.getStringExtra("fragment");





        System.out.println("getgetgetget   "+getStringFromFragment);

        //CALL METHOD
        ChooseGameFragmentMenu();



    }


    public void ChooseGameFragmentMenu()
    {

        // if getting position 0 go to the synonym game
        if(intValue == 0 && getStringFromFragment.equals(gamefrag)){

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new GuessSynonymMenuFragment()).commit();

        }else if(intValue == 1 && getStringFromFragment.equals(gamefrag))
        {


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new GuessWordMenuFragment()).commit();
        }

        else if(intValue == 0 && getStringFromFragment.equals(chatbot))
        {




            ChatbotsmenuFragment nextFrag= new ChatbotsmenuFragment();
            final Bundle bundle = new Bundle();
            bundle.putString("level","Start-greetings");
            nextFrag.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, nextFrag)
                    .addToBackStack(null)
                    .commit();


            // do this on menuchatbot
//            ChatBotFragment nextFrag= new ChatBotFragment();
//            final Bundle bundle = new Bundle();
//            bundle.putString("level","Start-weather(1)");
//            nextFrag.setArguments(bundle);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, nextFrag)
//                    .addToBackStack(null)
//                    .commit();
        }
        else {

//            //getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container,new ChatBotFragment()).commit();




            ChatbotsmenuFragment nextFrag= new ChatbotsmenuFragment();
            final Bundle bundle = new Bundle();
            bundle.putString("level","Start-weather(1)");
            nextFrag.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, nextFrag)
                    .addToBackStack(null)
                    .commit();



        }



    }




    }






