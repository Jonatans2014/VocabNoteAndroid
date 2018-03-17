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



        System.out.println(intValue);

        //CALL METHOD
        ChooseGameFragmentMenu();



    }


    public void ChooseGameFragmentMenu()
    {

        // if getting position 0 go to the synonym game
        if(intValue == 0){

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new GuessSynonymMenuFragment()).commit();

        }else
        {


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new GuessWordGameFragment()).commit();
        }

    }





}
