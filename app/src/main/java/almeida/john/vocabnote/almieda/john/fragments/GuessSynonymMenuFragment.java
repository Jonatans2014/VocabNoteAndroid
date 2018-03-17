package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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

import almeida.john.vocabnote.R;

public class GuessSynonymMenuFragment extends Fragment implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_guess_synonym_menu, container, false);


        TextView text1 = (TextView) view.findViewById(R.id.play);


        text1.setOnClickListener(this);
        return view;



    }




    @Override
    public void onClick(View view) {


//        Intent fbdata = new Intent(getContext(), SynonymGamePlayActivity.class);
//
//        // getProfileInformationFacebook(loginResult.getAccessToken());
//        startActivity(fbdata);


        GuessSynonymGameFragment nextFrag= new GuessSynonymGameFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, nextFrag)
                .addToBackStack(null)
                .commit();


    }
}
