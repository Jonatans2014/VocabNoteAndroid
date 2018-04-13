package almeida.john.vocabnote.almieda.john.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import almeida.john.vocabnote.R;


public class ChatbotsmenuFragment extends Fragment {



    TextView Play,about,innstruction;
    String getLvl;
    String level;
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_chatbotsmenu, container, false);


        Play = (TextView) view.findViewById(R.id.play);
        about = (TextView) view.findViewById(R.id.memo) ;
        innstruction = (TextView) view.findViewById(R.id.instr) ;






        Bundle bundle = getArguments();
        if (bundle != null)
        {
            level = bundle.getString("level");
        }

        getLvl = level;


        Play.setOnClickListener(PlayListener);
        about.setOnClickListener(aboutListener);
        innstruction.setOnClickListener(instrListener);



        return view;
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener PlayListener = new View.OnClickListener() {
        public void onClick(View v) {


                ChatBotFragment nextFrag= new ChatBotFragment();
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


    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener aboutListener = new View.OnClickListener() {
        public void onClick(View v) {


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getContext());

            // set title
            alertDialogBuilder.setTitle("Texts");


            if(level.equals("Start-greetings"))
            {
                // set dialog message
                alertDialogBuilder.setMessage(R.string.greetings);
            }
            else
            {
                // set dialog message
                alertDialogBuilder.setMessage(R.string.weather);
            }


            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    };
    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener instrListener = new View.OnClickListener() {
        public void onClick(View v) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getContext());

            // set title
            alertDialogBuilder.setTitle("Instructions");

            // set dialog message
            alertDialogBuilder.setMessage(R.string.chatbotinstructions);

            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    };
}
