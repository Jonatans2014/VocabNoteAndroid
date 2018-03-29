package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import almeida.john.vocabnote.Api;
import almeida.john.vocabnote.R;
import almeida.john.vocabnote.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GuessWordGameFragment extends Fragment implements  View.OnClickListener{



    //Override
    public List<Classification> UserClass = new ArrayList<>();
    public  List<Classification> getdata =  new ArrayList<>();
    public  List<WordsList> getWordList = new ArrayList<>();
    public  LinkedList<String> getSplit =   new LinkedList<String>();
    public LinkedList<String> checkSplit = new LinkedList<>();
    public LinkedList<String> allWord =    new LinkedList<String>();


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
    ImageView mShowDialog;
    TextView pointstv;
    String Category;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View drawer = inflater.inflate(R.layout.gamerecyclerview, container, false);

        // handler = new Handler() ;
       // new GuessWordGameFragment.CallbackTask().execute(dictionaryEntries());


        dash = "";

        pointstv = (TextView) drawer.findViewById(R.id.tvpoints);

        getHelpString =  "help1";
        mShowDialog = (ImageView) drawer.findViewById(R.id.helpV);
//
//        mShowDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
//
//                View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
//
//                final TextView mEmail = (TextView) mView.findViewById(R.id.def);
//
//                mEmail.setText("this is definition");
////
////                final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
////                Button mLogin = (Button) mView.findViewById(R.id.btnLogin);
//
//
//                mBuilder.setPositiveButton("Exit!", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                mBuilder.setView(mView);
//                final AlertDialog dialog = mBuilder.create();
//                dialog.show();
//
//            }
//        });
        setTimer = (TextView)drawer.findViewById(R.id.TVtimer);
        ChosenWord = (TextView) drawer.findViewById(R.id.guess_letters) ;
        recyclerView = (RecyclerView) drawer.findViewById(R.id.rec);
        //helpIcon =  (ImageView) drawer.findViewById(R.id.helpV);

        addLife = (ImageView) drawer.findViewById(R.id.addLifeIV);
        giveAletter = (ImageView) drawer.findViewById(R.id.letterIconIMV);



        lifeRecyclerV = (RecyclerView) drawer.findViewById(R.id.LIFE);

        //Instance of GamesAddon
        gamesAddon = new GamesAddon(3,0,0,0);

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


        String input = setTimer.getText().toString();


        setTimer();


        return drawer;
    }



    private String dictionaryEntries() {
        final String language = "en";
        final String word = "head";
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
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
                setTimer.setText("0");
                
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

                        View mView = getLayoutInflater().inflate(R.layout.gamestatts, null);

                        final TextView mEmail = (TextView) mView.findViewById(R.id.avgScore);
                        final TextView mScore = (TextView) mView.findViewById(R.id.TVscore);
                        final TextView mCorrect = (TextView) mView.findViewById(R.id.correctTV);
                        final TextView mInCorrect = (TextView) mView.findViewById(R.id.IncorrectTV);



                         mEmail.setText("this is definition");
                         mScore.setText((Integer.toString(points)));
                         mCorrect.setText((Integer.toString(getCorrect)));
                         mInCorrect.setText((Integer.toString(getIncorrect)));
//                       mCorrect.setText
//                final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
//                Button mLogin = (Button) mView.findViewById(R.id.btnLogin);


                        mBuilder.setPositiveButton("Exit!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();



            }
        }.start();

    }
    @Override
    public void onClick(View view) {

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.addLifeIV: {


                gamesAddon.addLife();
                recyclerView.setAdapter(adapter);
                addLife.setVisibility(View.GONE);

                break;

            }

            case R.id.letterIconIMV: {
                ChosenWord.append(checkSplit.getLast());
                checkSplit.removeLast();

                giveAletter.setVisibility(View.GONE);
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

        //
//      //  private final String[] mPlaces;
//        //private final Drawable[] mPlacePictures;
        public lifeAdapter(Context context) {
//            Resources resources = context.getResources();
//            //mPlaces = resources.getStringArray(R.array.places);
//            //TypedArray a = resources.obtainTypedArray(R.array.places_picture);
//            mPlacePictures = new Drawable[a.length()];
//            for (int i = 0; i < mPlacePictures.length; i++) {
//                mPlacePictures[i] = a.getDrawable(i);
//            }
//            a.recycle();
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

                    //System.out.println( "this is userclass"+UserClass.get(i).getWord().toString());

                }


//                System.out.println("this is linkedlist"+allWord.size());
//
//                // GET ALL WORDS IN THE LINKED LIST
//                   for(int i=0; i < allWord.size(); i++)
//                   {
//                       System.out.println("this is values of linkedlist"+allWord.get(i));
//                   }

                getRandomWordFromList();

            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {

            }
        });


    }






    public void getRandomWordFromList()
    {

        String WordRchosen;

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


        pointstv.setText((Integer.toString(points)));
//        for(int i = 0; i <splitWord.length; i++)
//        {
//            System.out.println("Word Split  " + splitWord[i]);
//
//            getSplit.addFirst(splitWord[i]);
//
//            checkSplit.addFirst(splitWord[i]);
//        }
//


        for(int i = 0; i < splitWord.length; i++) {
            ChosenWord.setText(dash);
        }

        // level of the game

        if(level.equals("easy") && splitWord.length <=4 )
        {
            setTimer.setText(level);


            for(int i = 0; i <splitWord.length; i++)
            {
                System.out.println("Word Split  " + splitWord[i]);

                getSplit.addFirst(splitWord[i]);

                checkSplit.addFirst(splitWord[i]);
            }

        }
        else if(level.equals("medium") && splitWord.length <=5)
        {

            setTimer.setText(level);


            for(int i = 0; i <splitWord.length; i++)
            {
                System.out.println("Word Split  " + splitWord[i]);

                getSplit.addFirst(splitWord[i]);

                checkSplit.addFirst(splitWord[i]);
            }

        }
       else if(level.equals("hard") && splitWord.length>5 )
        {
            setTimer.setText(level);


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
                        Toast.makeText(getContext(),"Incorrect letter",Toast.LENGTH_SHORT).show();

                        getIncorrect =gamesAddon.increaseIncorrect();

                        gamesAddon.removeLife();

                        System.out.println("this is life  " + gamesAddon.getLife());

                        recyclerView.setAdapter(adapter);
                    }
                    if(checkSplit.size() == 0)
                    {

                        gamesAddon.addTimertoLinkedListAndReset();

                       getCorrect= gamesAddon.increaseCorrect();

                        Toast.makeText(getContext(),"Welll Done",Toast.LENGTH_SHORT).show();

                        points = gamesAddon.addPoints(getHelpString);
                        Classifications.clear();
                        getRandomWordFromList();

                        System.out.println("hey hey heyy finished");


                        gamesAddon.startTimer();
//
//                                StartTime = SystemClock.uptimeMillis();
//                                handler.postDelayed(runnable, 0);
                    }
                }
            });
        }
        @Override
        public int getItemCount() {
            return Classifications.size();
        }
    }
}
