package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Map;

import almeida.john.vocabnote.R;


public class ChatbotsmenuFragment extends Fragment {


    RadioButton easy,medium,hard;
    TextView text1,about,innstruction;
    String getLvl;
    String[] message;
    Map map;
    String level;
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_chatbotsmenu, container, false);


        text1 = (TextView) view.findViewById(R.id.play);
        about = (TextView) view.findViewById(R.id.about) ;
        innstruction = (TextView) view.findViewById(R.id.instr) ;






        Bundle bundle = getArguments();
        if (bundle != null)
        {
            level = bundle.getString("level");
        }

        getLvl = level;


        text1.setOnClickListener(mCorkyListener);

        return view;
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
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
}
