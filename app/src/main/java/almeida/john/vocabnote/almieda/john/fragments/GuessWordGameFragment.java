package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import almeida.john.vocabnote.Api;
import almeida.john.vocabnote.MainActivity;
import almeida.john.vocabnote.R;
import almeida.john.vocabnote.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class GuessWordGameFragment extends Fragment implements  View.OnClickListener{



    //Override
    public List<Classification> UserClass = new ArrayList<>();
    public  List<Classification> getdata =  new ArrayList<>();
    public  List<WordsList> getWordList = new ArrayList<>();
    public  LinkedList<String> getSplit =   new LinkedList<String>();
    public LinkedList<String> checkSplit = new LinkedList<>();
    public LinkedList<String> allWord =    new LinkedList<String>();
    PieChart piechart;
    int addSecondPS =0;
    public CountDownTimer yourCountDownTimer;

    int highestScore =0;
    float Avg =0;

    int points;
    public String getHelpString;

    public List<DicInfo> getDicdata =  new ArrayList<>();
    int ListSize = 0;
    TextView Def;
    TextView Example;

    String def = null;

    String example = null;

    String lexicalCategory;
    String getDialect;
    ImageView Pronunciation;
    String DictWord;
    int getCorrect;
    int getIncorrect;
    String WordRchosen;


    Button start, pause, reset, lap ;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds ;

    public RecyclerView recyclerView, lifeRecyclerV;
    public ImageView helpIcon;
    String[] ClassList ;
    String[] WordList;
    String[] splitWord;
    String dash;
    GuessWordGameFragment.ContentAdapter adapter;
    GuessWordGameFragment.lifeAdapter lifeAdapter;
    //GuessWordGameFragment.ContentAdapter adapter;
    ArrayList <String> getThreeWords = new ArrayList<>();
    LinkedList<String>ListElementsArrayList = new LinkedList<>();
    TextView ChosenWord, choice1, choice2, choice3;
    ImageView addLife;
    ImageView giveAletter;
    GamesAddon gamesAddon;
    String Dict;
    String level;
    TextView setTimer;
    ImageView helpIV;
    TextView pointstv;
    String Category;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View drawer = inflater.inflate(R.layout.gamerecyclerview, container, false);

        dash = "";

        pointstv = (TextView) drawer.findViewById(R.id.tvpoints);

        getHelpString =  "help1";
        helpIV = (ImageView) drawer.findViewById(R.id.helpV);





// Save the changes in SharedPreferences


        setTimer = (TextView)drawer.findViewById(R.id.TVtimer);
        ChosenWord = (TextView) drawer.findViewById(R.id.guess_letters) ;
        recyclerView = (RecyclerView) drawer.findViewById(R.id.rec);
        //helpIcon =  (ImageView) drawer.findVieonfwById(R.id.helpV);

        addLife = (ImageView) drawer.findViewById(R.id.addLifeIV);
        giveAletter = (ImageView) drawer.findViewById(R.id.letterIconIMV);



        lifeRecyclerV = (RecyclerView) drawer.findViewById(R.id.LIFE);

        //Instance of GamesAddon
        gamesAddon = new GamesAddon();

        lifeAdapter = new GuessWordGameFragment.lifeAdapter(lifeRecyclerV.getContext());

        //lifeAdapter = new GuessWordGameFragment.lifeAdapter(recyclerView.getContext());

        lifeRecyclerV.setAdapter(lifeAdapter);
        lifeRecyclerV.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);

        lifeRecyclerV.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        lifeRecyclerV.setHasFixedSize(true);
//        // Set padding for Tiles

        lifeRecyclerV.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));



//        helpIcon.setOnClickListener(this);
        addLife.setOnClickListener(this);
        giveAletter.setOnClickListener(this);
        helpIV.setOnClickListener(this);


        bindCategoryOrWordsToRecyclerView();
//
//        StartTime = SystemClock.uptimeMillis();
//        handler.postDelayed(runnable, 0);

        //reset.setEnabled(false);


        //call timer
        //gamesAddon.setTimer(setTimer);

        gamesAddon.startTimer();

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            level = bundle.getString("level");
        }

        setTimer();


        return drawer;
    }



    private String dictionaryEntries() {
        final String language = "en";
        final String word = WordRchosen;
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id ;
    }
    //in android calling network requests on the main thread forbidden by default
    //create class to do async job
    private class CallbackTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            //TODO: replace with your own app id and app key
            final String app_id = "feffd9c7";
            final String app_key = "7ff5792182345d6d63e5790990fd6aeb";
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setRequestProperty("app_id",app_id);
                urlConnection.setRequestProperty("app_key",app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                return stringBuilder.toString();

            }
            catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject js = new JSONObject(result);
                JSONArray results = js.getJSONArray("results");

                for(int i = 0; i<results.length(); i++){
                    JSONObject lentries = results.getJSONObject(i);
                    JSONArray la = lentries.getJSONArray("lexicalEntries");
                    for(int j=0;j<la.length();j++){
                        JSONObject entries = la.getJSONObject(j);
                        JSONArray e = entries.getJSONArray("entries");

                        JSONArray pronunciation= entries.getJSONArray("pronunciations");

                        for(int p =0; p<pronunciation.length(); p++)
                        {
                            JSONObject pronunObj = pronunciation.getJSONObject(p);



                            // System.out.println("this is file "+getDialect);
                        }

                        for(int k=0;k<e.length();k++){
                            JSONObject senses = e.getJSONObject(k);
                            JSONArray s = senses.getJSONArray("senses");

                            for(int h = 0; h < s.length(); h++)
                            {
                                JSONObject d = s.getJSONObject(h);
                                example =  d.optString("examples");

                                def = d.optString("definitions");

                                //get rid of none words and the name text:
                                String replaceNoneWordsExample =example.replaceAll("[^\\w-]+", " ");
                                String replaceNoneWordsDefinition =def.replaceAll("[^\\w-]+", " ");

                                DicInfo dicInfo = new DicInfo(replaceNoneWordsDefinition,replaceNoneWordsExample,getDialect);
                                getDicdata.add(dicInfo);


                                System.out.println("it worked hehhehehehee" + getDicdata.get(0).getDefinitions());


                                // lexicalCategory

                            }

                        }


                    }
                }


                alertDialog(getDicdata.get(0).getDefinitions());
            }




            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    public void alertDialog(String def)
    {
        // commit changes
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View mView = getLayoutInflater().inflate(R.layout.helptext, null);

        TextView text = (TextView) mView.findViewById(R.id.helptxt);


        text.setText(def);
        builder.setTitle("Definition");

        builder.setPositiveButton("             Continue!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        builder.setView(mView);
        final AlertDialog dialog = builder.create();
        dialog.show();

    }


    //setTimer
    public  void setTimer()
    {
        String getdone;



        try {
         yourCountDownTimer=  new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                setTimer.setText(""+String.format("%d:%d ",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {




                ///there's a bugg when we leave the page timer keeps running need to do something when going back.
                setTimer.setText("0");


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

                alertDialog();


            }
        }.start();}
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }





    public void alertDialog()
    {

        try{

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

        View mView = getLayoutInflater().inflate(R.layout.fragment_peformance_analysis, null);

        final TextView mEmail = (TextView) mView.findViewById(R.id.avgScore);
        final TextView HigestScore = (TextView) mView.findViewById(R.id.highestScore);
        final TextView mScore = (TextView) mView.findViewById(R.id.TVscore);
        final TextView mCorrect = (TextView) mView.findViewById(R.id.correctTV);
        final TextView mInCorrect = (TextView) mView.findViewById(R.id.IncorrectTV);
        piechart =  mView.findViewById(R.id.piechart);


            System.out.println("This is highestScore   " +gamesAddon.getHighestScore() );
            // set highestscore to highest
            if(gamesAddon.getHighestScore() < points)
            {
                gamesAddon.setHighestScore(points);
            }


            //add overallHighestScore from all the games.
            if(gamesAddon.getHighestScore() > gamesAddon.getOverAllHighestScore())
            {
                gamesAddon.setOverAllHighestScore(gamesAddon.getHighestScore());
            }


            mEmail.setText(String.valueOf(Avg));
            mScore.setText((Integer.toString(points)));

        HigestScore.setText((Integer.toString(gamesAddon.getHighestScore())));
        gamesAddon.setOverAllScore(points);
        gamesAddon.setOverallIncorret(getIncorrect);
        gamesAddon.setOverallCorrect(getCorrect);



        System.out.println("points " +gamesAddon.getOverAllScore() );
        System.out.println("correct " +  gamesAddon.getOverAllScore());


            //add overallsocre, correct and incorrect values to sharedPerefences
            SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putInt("OverallScore",gamesAddon.getOverAllScore());
            editor.putInt("OverallIncorret", gamesAddon.getOverallIncorret());
            editor.putInt("Overallcorret", gamesAddon.getOverallCorrect());
            editor.putInt("OverallHighestScore", gamesAddon.getOverAllHighestScore());
            editor.apply();



            SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE); // getting Integer
            int pageNumber=prefs.getInt("OverallScore", 0);



            System.out.println("WORKS  " + pageNumber);


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
                GuessWordGameFragment nextFrag= new GuessWordGameFragment();
                final Bundle bundle = new Bundle();
                bundle.putString("level","medium");
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

        points = gamesAddon.removePoints();

        pointstv.setText((Integer.toString(points)));

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.addLifeIV: {


                gamesAddon.addLife();
                recyclerView.setAdapter(adapter);
                addLife.setVisibility(View.GONE);

                gamesAddon.removePoints();

                break;

            }

            case R.id.letterIconIMV: {

                gamesAddon.removePoints();
                ChosenWord.append(checkSplit.getLast());
                checkSplit.removeLast();

                giveAletter.setVisibility(View.GONE);
                break;

            }
            case R.id.helpV:
            {

                new GuessWordGameFragment.CallbackTask().execute(dictionaryEntries());

                break;
            }
        }
    }


    public static class ViewHolderLife extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView tvpoints;
        public ViewHolderLife(LayoutInflater inflater, ViewGroup parent) {

            // this code to be used to connect the fragments
            super(inflater.inflate(R.layout.lifelayout, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.lifeImgV);

        }
    }
    /**
     * Adapter to display recycler view.
     */
    public  class lifeAdapter extends RecyclerView.Adapter<GuessWordGameFragment.ViewHolderLife> {
        // Set numbers of Tiles in RecyclerView.
        public List<GamesAddon> getdata = new ArrayList<>();

        public lifeAdapter(Context context) {

        }

        @Override
        public GuessWordGameFragment.ViewHolderLife onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GuessWordGameFragment.ViewHolderLife(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final GuessWordGameFragment.ViewHolderLife holder, final int position) {
            int points = 0;


        }

        @Override
        public int getItemCount(


        ) {
            return gamesAddon.getLife();
        }




    }
    // this has been repeated many times, it has to be refactored

    public void bindCategoryOrWordsToRecyclerView()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                //Here we are using the GsonConverterFactory to directly convert json data to object
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<UserInfo>> call = api.getUserList();

        call.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                //List of objects
                List<UserInfo> users =  response.body();
                //Creating an String array for the ListView

                //loop trough UserClass Variable and assign words to UserWords
                for(int i = 0; i < users.size(); i ++)
                {

                    UserClass = users.get(i).getClassification();

                    System.out.println("Class"+ UserClass );


                }

                ClassList = new String[UserClass.size()];

                //  System.out.println(UserWords);

                // WordList is now working try the same with another fragment
                //Log.e("getClass", UserWords.toString());
                WordList = new String[getWordList.size()];
                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < UserClass.size(); i++) {

                    //  ClassList[i] = UserClass.get(i).getClassification();


                    ClassList[i] = UserClass.get(i).getClassification();
                    getWordList  = UserClass.get(i).getWord();
                    for(int j = 0; j <getWordList.size(); j++)
                    {
//                        WordList[j] = getWordList.get(j).getWord();
//
//                        System.out.println("Words"+ WordList[j] );
                        allWord.addFirst(getWordList.get(j).getWord());

                    }
                }
                getRandomWordFromList();

            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {

            }
        });


    }






    public void getRandomWordFromList()
    {



        int min = 0;
        int max = allWord.size();

//
//        ArrayList<Integer> number = new ArrayList<Integer>();
//        for (int i = 0; i <=2; ++i) number.add(i);

        Collections.shuffle(allWord);

        System.out.println("Shuffling" + allWord);
        //get a word
        WordRchosen = allWord.getFirst();
        // word split
        splitWord = WordRchosen.split("(?!^)");





        for(int i = 0; i < splitWord.length; i++) {
            ChosenWord.setText(dash);
        }

        // level of the game

        if(level.equals("easy") && splitWord.length <=4 )
        {


            for(int i = 0; i <splitWord.length; i++)
            {
                System.out.println("Word Split  " + splitWord[i]);

                getSplit.addFirst(splitWord[i]);

                checkSplit.addFirst(splitWord[i]);
            }

        }
        else if(level.equals("medium") && splitWord.length <=5)
        {




            for(int i = 0; i <splitWord.length; i++)
            {
                System.out.println("Word Split  " + splitWord[i]);

                getSplit.addFirst(splitWord[i]);

                checkSplit.addFirst(splitWord[i]);
            }

        }
       else if(level.equals("hard") && splitWord.length>5 )
        {


            for(int i = 0; i <splitWord.length; i++)
            {
                System.out.println("Word Split  " + splitWord[i]);

                getSplit.addFirst(splitWord[i]);

                checkSplit.addFirst(splitWord[i]);
            }
        }

        else
        {
            getRandomWordFromList();
        }


        Collections.shuffle(getSplit);

        adapter = new GuessWordGameFragment.ContentAdapter(getSplit);

        recyclerView.setAdapter(adapter);

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_guess_word_game, parent, false));


            description = (TextView) itemView.findViewById(R.id.letter);



        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<GuessWordGameFragment.ViewHolder> {
        // Set numbers of List in RecyclerView.

        private  LinkedList<String> Classifications;

        public  List<Classification> senddata =  new ArrayList<>();

        public ContentAdapter(LinkedList<String> getdata) {

            this.Classifications = getdata;


        }





        @Override
        public GuessWordGameFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GuessWordGameFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);


        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final String selectedCategory = Classifications.get(position % Classifications.size());
            holder.description.setText(Classifications.get(position % Classifications.size()));
            holder.description.setOnClickListener(new View.OnClickListener() {

                boolean firstClick = true;
                @Override
                public void onClick(View view) {
                    // adapter.notifyDataSetChanged();
                    if(firstClick == true)
                    {
                        // ChosenWord.setText("-");
                        firstClick = false;
                    }
                    if(selectedCategory.equals(checkSplit.getLast()))
                    {
                        //  Toast.makeText(getContext(),"Welll Done",Toast.LENGTH_SHORT).show();
                        ChosenWord.append(checkSplit.getLast());
                        checkSplit.removeLast();

                    }
                    else
                    {

                        if(gamesAddon.getLife() ==1)
                        {

                            alertDialog();
                        }



                        getIncorrect =gamesAddon.increaseIncorrect();

                        gamesAddon.removeLife();



                        recyclerView.setAdapter(adapter);
                    }
                    if(checkSplit.size() == 0)
                    {


                        showWord();

                        gamesAddon.addTimertoLinkedListAndReset();
                        getCorrect= gamesAddon.increaseCorrect();
                        Toast.makeText(getContext(),"Welll Done",Toast.LENGTH_SHORT).show();
                        points = gamesAddon.addPoints();
                        pointstv.setText((Integer.toString(points)));

                        Classifications.clear();
                        getRandomWordFromList();

                        gamesAddon.startTimer();


                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return Classifications.size();
        }
    }



    public void  showWord()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

        View mView = getLayoutInflater().inflate(R.layout.dialogjumbledword, null);

        final TextView word = (TextView) mView.findViewById(R.id.wordTV);


        word.setText(WordRchosen);
        mBuilder.setNegativeButton("Continue!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
