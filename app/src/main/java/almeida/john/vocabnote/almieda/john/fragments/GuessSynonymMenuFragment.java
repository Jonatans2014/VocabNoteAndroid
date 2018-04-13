package almeida.john.vocabnote.almieda.john.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import almeida.john.vocabnote.R;

public class GuessSynonymMenuFragment extends Fragment implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_guess_synonym_menu, container, false);


        TextView play = (TextView) view.findViewById(R.id.play);
        TextView Instruction = (TextView) view.findViewById(R.id.instra);
        TextView about = (TextView) view.findViewById(R.id.memo);




        play.setOnClickListener(this);
        Instruction.setOnClickListener(this);
        about.setOnClickListener(this);
        return view;



    }




    @Override
    public void onClick(View view) {


//        Intent fbdata = new Intent(getContext(), SynonymGamePlayActivity.class);
//
//        // getProfileInformationFacebook(loginResult.getAccessToken());
//        startActivity(fbdata);



        switch (view.getId()) {
            case R.id.play: {

                GuessSynonymGameFragment nextFrag= new GuessSynonymGameFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag)
                        .addToBackStack(null)
                        .commit();

                break;
            }
            case R.id.instra:
            {

                alertDialogInstruction();
                System.out.println(" hey hey ");
                break;
            }

            case R.id.memo:
            {

                alertDialogAboutGame();
                break;
            }

        }



    }



    public void alertDialogAboutGame()
    {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        // set title
        alertDialogBuilder.setTitle("About");

        // set dialog message
        alertDialogBuilder.setMessage(R.string.synonymabout_textsynonymgame);

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }




    public void alertDialogInstruction()
    {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        // set title
        alertDialogBuilder.setTitle("Instructions");

        // set dialog message
        alertDialogBuilder.setMessage(R.string.synonyminstructions);



        AlertDialog alertDialog = alertDialogBuilder.create();




        // show it
        alertDialog.show();
    }

}
