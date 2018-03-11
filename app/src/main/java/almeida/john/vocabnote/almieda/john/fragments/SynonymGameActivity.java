package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.service.carrier.CarrierService;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import almeida.john.vocabnote.R;

public class SynonymGameActivity extends AppCompatActivity implements View.OnClickListener {


    TextView mainWord, text1, text2, text3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonym_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        text1 = (TextView) findViewById(R.id.play);
        mainWord = (TextView) findViewById(R.id.word2);
        text2 = (TextView) findViewById(R.id.word3);
        text3 = (TextView) findViewById(R.id.word1);


        text1.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {


        Intent fbdata = new Intent(getBaseContext(), SynonymGamePlayActivity.class);

        // getProfileInformationFacebook(loginResult.getAccessToken());
        startActivity(fbdata);


    }
}
