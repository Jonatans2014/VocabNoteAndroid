package almeida.john.vocabnote.almieda.john.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import almeida.john.vocabnote.MainActivity;
import almeida.john.vocabnote.R;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;


/**
 * Created by John on 27/11/2017.
 */

public class ChatBotFragment extends Fragment implements View.OnClickListener{


    PieChart piechart;
    AIService aiService;

    public RecyclerView recyclerView;
    EditText editText;
    RelativeLayout addBtn;
    AIRequest aiRequest;
    AIDataService aiDataService;
    Boolean flagFab = true;
    TextView PointsTV;
    LinkedList<ChatMessage> chat = new LinkedList<>();
    ImageView fab_img;
    ContentAdapter adapter;
    String message;
    String level;

    TextView setTimer;
    int points;
    Boolean firstQuestion = false;

    int getCorrect;
    int getIncorrect;
    float Avg =0;
    int addSecondPS =0;
    ChatMessage chatbotMessage;
    ImageView helptxtUser;
    RecyclerView  txtRecyclerV;


    GamesAddon gamesAddon = new GamesAddon();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


        final View drawer = inflater.inflate(R.layout.chatbotrecyclerview, container, false);



        recyclerView =  drawer.findViewById(R.id.recyclerView);
        editText = (EditText)drawer.findViewById(R.id.editText);
        addBtn = (RelativeLayout)drawer.findViewById(R.id.addBtn);
        setTimer =  (TextView) drawer.findViewById(R.id.timer);
        PointsTV =  (TextView) drawer.findViewById(R.id.tvpoints);
        helptxtUser = (ImageView) drawer.findViewById(R.id.helptxt);






        setTimer();

        // dialogflow declaration
        final AIConfiguration config = new AIConfiguration("f4a9d1c62f0c46c4b73e728268e75bfc",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(getContext(), config);
        aiDataService = new AIDataService(config);
        aiRequest = new AIRequest();


        addBtn.setOnClickListener(this);



        Bundle bundle = getArguments();
        if (bundle != null)
        {
            level = bundle.getString("level");
        }



        message = level;

        System.out.println("hihihhii" + message);
        getAiResponse();



        adapter = new ContentAdapter(chat);
        recyclerView.setHasFixedSize(true);
//        // Set padding for Tiles
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        helptxtUser.setOnClickListener(this);

        return drawer;

    }


    public void getAiResponse()
    {

        aiRequest.setQuery(message);
        new AsyncTask<AIRequest,Void,AIResponse>(){

            @Override
            protected AIResponse doInBackground(AIRequest... aiRequests) {
                final AIRequest request = aiRequests[0];
                try {
                    final AIResponse response = aiDataService.request(aiRequest);
                    return response;
                } catch (AIServiceException e) {
                }
                return null;
            }
            @Override
            protected void onPostExecute(AIResponse response) {
                if (response != null) {



                    Result result = response.getResult();



                    String reply = result.getFulfillment().getSpeech();
                     chatbotMessage = new ChatMessage(reply, "bot");

                    chat.addLast(chatbotMessage);

                    recyclerView.setAdapter(adapter);
                    //ref.child("chat").push().setValue(chatMessage);

                    System.out.println("work work work work work " + result +"  " + reply );

                    //Need to fix a bug

                    //add score
                    if(reply.equals("Sorry, I didn't get that."))
                    {
                        points = gamesAddon.removePoints();

                    }
                    else if(firstQuestion == true)
                    {
                        points =  gamesAddon.addPoints();
                    }

                    PointsTV.setText(String.valueOf(points));
                    firstQuestion = true;



                }


            }


        }.execute(aiRequest);



    }





    //setTimer
    public  void setTimer()
    {
        String getdone;
        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                setTimer.setText(""+String.format("%d:%d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {





                System.out.println("This is highestScore   " +gamesAddon.getHighestScore() );
                // set highestscore to highest
                if(gamesAddon.getHighestScore() < points)
                {
                    gamesAddon.setHighestScore(points);
                }

                //shared prefff
                //Save score
//                SharedPreferences myScore = getActivity().getPreferences(Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = myScore.edit();
//                editor.putInt("score", PointsTV);
//                editor.commit();



                // int  score = myScore.getInt("score", 0);
                for(int i =0 ; i < gamesAddon.getListUserTimeGuessingWord().size(); i ++)
                {

                    addSecondPS += gamesAddon.getListUserTimeGuessingWord().get(i);


                    System.out.println("this is the timer p/s   " +gamesAddon.getListUserTimeGuessingWord().get(i));
                }

                if(addSecondPS > 0)
                {
                    Avg = addSecondPS / gamesAddon.getListUserTimeGuessingWord().size();
                }


                dialogAlert();



            }
        }.start();

    }

    public void dialogAlert()
    {
        try
        {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

        View mView = getLayoutInflater().inflate(R.layout.fragment_peformance_analysis, null);

        final TextView mEmail = (TextView) mView.findViewById(R.id.avgScore);
        final TextView HigestScore = (TextView) mView.findViewById(R.id.highestScore);
        final TextView mScore = (TextView) mView.findViewById(R.id.TVscore);
        final TextView mCorrect = (TextView) mView.findViewById(R.id.correctTV);
        final TextView mInCorrect = (TextView) mView.findViewById(R.id.IncorrectTV);
        piechart =  mView.findViewById(R.id.piechart);


        mEmail.setText(String.valueOf(Avg));
        mScore.setText((Integer.toString(points)));
//                         mCorrect.setText((Integer.toString(getCorrect)));
//                         mInCorrect.setText((Integer.toString(getIncorrect)));
        HigestScore.setText((Integer.toString(gamesAddon.getHighestScore())));

        gamesAddon.setOverAllScore(points);

//                       mCorrect.setText
//                       final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
//                       Button mLogin = (Button) mView.findViewById(R.id.btnLogin);




        System.out.println("this is PointsTV  " + gamesAddon.getOverAllScore());
        piechart.setUsePercentValues(true);
        piechart.getDescription().setEnabled(false);
        piechart.setExtraOffsets(5,10,5,5);

        piechart.setDragDecelerationFrictionCoef(0.95f);
        piechart.setDrawHoleEnabled(true);
        piechart.setHoleColor(Color.WHITE);
        piechart.setTransparentCircleRadius(61f);


        ArrayList<PieEntry> yvalues = new ArrayList<>();
        yvalues.add(new PieEntry(getCorrect,"Correct"));
        yvalues.add(new PieEntry(getIncorrect,"Incorrect"));



        final int[] MY_COLORS = {Color.rgb(65, 244, 199), Color.rgb(255,0,0)};
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c: MY_COLORS) colors.add(c);



        PieDataSet DataSet = new PieDataSet(yvalues,"");
        DataSet.setSliceSpace(3f);
        DataSet.setSelectionShift(5f);
        DataSet.setColors(colors);
        PieData data = new PieData(DataSet);
        data.setValueTextSize(20F);
        data.setValueTextColor(Color.BLACK);

        piechart.setData(data);


        mBuilder.setNegativeButton("Exit!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Fetch lists of users, classifications and Words.
                Intent fbdata = new Intent(getContext(), MainActivity.class);
                // getProfileInformationFacebook(loginResult.getAccessToken());
                startActivity(fbdata);

            }
        });

        mBuilder.setPositiveButton("              Play Again!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
                ChatBotFragment nextFrag= new ChatBotFragment();
                final Bundle bundle = new Bundle();
                bundle.putString("level",level);
                nextFrag.setArguments(bundle);
                getActivity()
                        .getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();}
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }












    @Override
    public void onClick(View view) {


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.addBtn: {




                 message = editText.getText().toString().trim();

                if (!message.equals("")) {

                    ChatMessage chatMessage = new ChatMessage(message, "user");

                    chat.addLast(chatMessage);

                    recyclerView.setAdapter(adapter);

                   // ref.child("chat").push().setValue(chatMessage);


                    getAiResponse();
                }


                editText.setText("");
                message = "";


                break;

            }


            case R.id.helptxt:
            {

                alertDialog();
                break;
            }
        }
    }

    @SuppressLint("ResourceType")
    public void alertDialog()
    {

        String  getext;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

        View mView = getLayoutInflater().inflate(R.layout.helptext, null);

        TextView text = (TextView) mView.findViewById(R.id.helptxt);

        Resources resources = this.getResources();

        getext = resources.getString(R.string.greetings);

        System.out.println(getext);

        text.setText(getext);

        mBuilder.setNegativeButton("Exit!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Fetch lists of users, classifications and Words.
                Intent fbdata = new Intent(getContext(), MainActivity.class);
                // getProfileInformationFacebook(loginResult.getAccessToken());
                startActivity(fbdata);

            }
        });

        mBuilder.setPositiveButton("             Continue!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView sender,reciever;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_chatbot, parent, false));

            sender = (TextView) itemView.findViewById(R.id.leftText);
            reciever = (TextView) itemView.findViewById(R.id.rightText);
        }
    }









    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ChatBotFragment.ViewHolder> {
        // Set numbers of List in RecyclerView.

        int points;

        private LinkedList<ChatMessage> Classifications;
        public List<Classification> senddata =  new ArrayList<>();

        public ContentAdapter(LinkedList<ChatMessage> getdata) {

            this.Classifications = getdata;


        }
        @Override
        public ChatBotFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ChatBotFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ChatBotFragment.ViewHolder holder, int position) {






            if (Classifications.get(position % Classifications.size()).getMsgUser().equals("user")) {

                holder.sender.setText(Classifications.get(position % Classifications.size()).getMsgText());
               // holder.rightText.setText(model.getMsgText());

                holder.sender.setVisibility(View.VISIBLE);
                holder.reciever.setVisibility(View.GONE);
                }
                else {

                holder.reciever.setText(Classifications.get(position % Classifications.size()).getMsgText());
                //holder.leftText.setText(model.getMsgText());

                holder.sender.setVisibility(View.GONE);
                holder.reciever.setVisibility(View.VISIBLE);
                }

//           // final String selectedCategory = Classifications.get(position % Classifications.size());
//            if(Classifications.get(position % Classifications.size()).getMsgUser().equals("user"))
//            {
//
//            }
//            else
//            {
//                holder.reciever.setText(Classifications.get(position % Classifications.size()).getMsgText());
//            }


        }
        @Override
        public int getItemCount() {
            return Classifications.size();
        }
    }


}

