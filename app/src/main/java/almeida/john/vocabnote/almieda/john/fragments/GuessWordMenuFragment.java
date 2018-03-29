package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import almeida.john.vocabnote.R;


public class GuessWordMenuFragment extends Fragment implements  View.OnClickListener{


    RadioButton easy,medium,hard;
    TextView text1;
    String getLvl;
    Map map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_guess_word_menu, container, false);


        text1 = (TextView) view.findViewById(R.id.play);
        easy = (RadioButton) view.findViewById(R.id.RBeasy) ;
        medium = (RadioButton) view.findViewById(R.id.RBmedium);
        hard = (RadioButton) view.findViewById(R.id.RBhard);

        easy.setOnClickListener(this);
        medium.setOnClickListener(this);
        hard.setOnClickListener(this);
        text1.setOnClickListener(mCorkyListener);
        getLvl = "easy";



        return view;
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        public void onClick(View v) {

            GuessWordGameFragment nextFrag= new GuessWordGameFragment();
            final Bundle bundle = new Bundle();
            bundle.putString("level",getLvl);
            nextFrag.setArguments(bundle);
            getActivity()
                    .getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, nextFrag)
                    .addToBackStack(null)
                    .commit();
        }
    };




    @Override
    public void onClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.RBeasy: {
                if (checked) {


                    Toast.makeText(getContext(), "easy", Toast.LENGTH_SHORT).show();
                    getLvl = "easy";

                }
                break;
            }


            case R.id.RBmedium: {
                if (checked) {
                    Toast.makeText(getContext(), "medium", Toast.LENGTH_SHORT).show();
                    getLvl = "medium";
                }

                break;
            }

            case R.id.RBhard:
            {
                if(checked)
                {

                    Toast.makeText(getContext(),"hard",Toast.LENGTH_SHORT).show();
                    getLvl = "hard";
                }
                break;
            }
        }
    }
}

