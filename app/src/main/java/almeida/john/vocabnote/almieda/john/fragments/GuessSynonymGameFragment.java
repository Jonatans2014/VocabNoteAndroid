package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.os.AsyncTask;

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
import java.util.Random;
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


public class GuessSynonymGameFragment extends Fragment  implements View.OnClickListener{

    //Override
    public List<Classification> UserClass = new ArrayList<>();

    public  List<WordsList> getWordList = new ArrayList<>();
    public LinkedList<String> allWord =  new LinkedList<String>();
    public RecyclerView recyclerView,lifeRecyclerV;
    String[] ClassList ;
    String[] WordList;
    PieChart piechart;
    float Avg =0;
    int addSecondPS =0;
    static int counter;
    String [] getSynonym;
    int points;
    TextView setTimer;
    public String getHelpString;
    int getCorrect;
    int getIncorrect;
    GuessSynonymGameFragment.ContentAdapter adapter, lifeAdapter;


    ImageView life1,life2,life3;

    int SynonymObjectSize = 1;
    ArrayList <String> getThreeWords = new ArrayList<>();
    TextView ChosenWord, choice1, choice2, choice3;
    TextView pointstv;
    GamesAddon gamesAddon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        View view  =  inflater.inflate(R.layout.fragment_guess_synonym_game, container, false);


        //Instance of GamesAddon
         gamesAddon = new GamesAddon(3,0);



        getHelpString  = "none";

        bindCategoryOrWordsToRecyclerView();


        recyclerView = (RecyclerView) view.findViewById(R.id.synoyngameRec);


        gamesAddon.startTimer();

        pointstv = (TextView) view.findViewById(R.id.tvpoints);
        setTimer = (TextView) view.findViewById(R.id.TVtimer);

        adapter = new GuessSynonymGameFragment.ContentAdapter(recyclerView.getContext());





        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));



        ChosenWord = (TextView) view.findViewById(R.id.mainWord);

        choice1 = (TextView) view.findViewById(R.id.word1);

        choice2 = (TextView) view.findViewById(R.id.word2);
        choice3 = (TextView) view.findViewById(R.id.word3);


        //p/s
        gamesAddon.startTimer();

        //countdowntimer
        setTimer();
        //set onClick Listener
        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);



        return view;

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




                ///there's a bugg when we leave the page timer keeps running need to do something when going back.
                setTimer.setText("0");


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
//                editor.putInt("score", points);
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


                System.out.println("this is points  " + gamesAddon.getOverAllScore());
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

                        //Might add lvls

                        GuessSynonymGameFragment nextFrag= new GuessSynonymGameFragment();
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
                dialog.show();



            }
        }.start();

    }





    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView tvpoints;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {

            // this code to be used to connect the fragments
            super(inflater.inflate(R.layout.lifelayout, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.lifeImgV);
            tvpoints = (TextView) itemView.findViewById(R.id.tvpoints);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
//                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public  class ContentAdapter extends RecyclerView.Adapter<GuessSynonymGameFragment.ViewHolder> {
        // Set numbers of Tiles in RecyclerView.
        public  List<GamesAddon> getdata =  new ArrayList<>();

        //
//      //  private final String[] mPlaces;
//        //private final Drawable[] mPlacePictures;
        public ContentAdapter(Context context) {
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
        public GuessSynonymGameFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GuessSynonymGameFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final GuessSynonymGameFragment.ViewHolder holder, final int position) {
            int points = 0;


        }

        @Override
        public int getItemCount(


        ) {
            return gamesAddon.getLife();
        }





    }


    public void bindCategoryOrWordsToRecyclerView() {

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



    //find a randdom word from linked list and parse it to dictionary entries
    //get the synonym of a word

    public void getRandomWordFromList()
    {
        int min = 0;
        int max = allWord.size();

        Random r = new Random();
        int mainWord = r.nextInt(max - min) + min;
        int otherword = r.nextInt(
                max - min) + min;
//
//
//        ArrayList<Integer> number = new ArrayList<Integer>();
//        for (int i = 0; i <=2; ++i) number.add(i);

        Collections.shuffle(allWord);

        System.out.println("Shuffling" + allWord);

        pointstv.setText((Integer.toString(points)));

//        if(mainWord == otherword)
//        {
//            otherword =  r.nextInt(max - min) + min;
//        }
//        allWord.size();


//        System.out.println("this is values of linkedlist in get random"+  allWord.get(mainWord) + " plus " + "Random Number" + mainWord + "other word" + otherword);


        new GuessSynonymGameFragment.CallbackTask().execute(dictionaryEntries(allWord.getFirst()));




    }


    //dictionary Entries
    private String dictionaryEntries(String getWord) {
        final String language = "en";
        final String word = getWord;
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id + "/synonyms;antonyms";
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

            // System.out.println(result);

            if(result.contains("java.io.FileNotFoundException:"))
            {
                System.out.println("yah expection");
                getRandomWordFromList();

            }
            else
            {

                ChosenWord.setText(allWord.getFirst());



//
//                ArrayList<Integer> number = new ArrayList<Integer>();
//                for (int i = 0; i <=2; ++i) number.add(i);
//
//                Collections.shuffle(number);



                try {
                    JSONObject js = new JSONObject(result);
                    JSONArray results = js.getJSONArray("results");

                    for(int i = 0; i<results.length(); i++){
                        JSONObject lentries = results.getJSONObject(i);
                        JSONArray la = lentries.getJSONArray("lexicalEntries");
                        for(int j=0;j<SynonymObjectSize;j++){
                            JSONObject entries = la.getJSONObject(j);
                            JSONArray e = entries.getJSONArray("entries");
                            for(int k=0;k<SynonymObjectSize;k++){
                                JSONObject senses = e.getJSONObject(k);
                                JSONArray s = senses.getJSONArray("senses");

                                for(int h = 0; h < SynonymObjectSize; h++)
                                {



                                    System.out.println(h);

                                    JSONObject d = s.getJSONObject(h);
                                    JSONArray de = d.getJSONArray("synonyms");
//                                    JSONArray anto = d.getJSONArray("antonyms");
                                    System.out.println(de);



                                    String getSyn = de.getString(0);
//                                    String getAnt = anto.getString(0);
//                                    System.out.println(getAnt);


                                    // replace none words with ,
                                    String replaceNoneWords =getSyn.replaceAll("[^\\w-]+", ",");

//                                    String replaceNoneWordsAnto =getAnt.replaceAll("[^\\p{L}\\p{Nd}]+", ",");

                                    getSynonym = replaceNoneWords.split(",");
//                                    String [] getAtonym = replaceNoneWordsAnto.split(",");

                                    System.out.println("replaceW"+replaceNoneWords);
                                }
                            }
                        }
                    }




                    getThreeWords.add(getSynonym[2]);
                    getThreeWords.add(allWord.getLast());
                    // change this to the middle value
                    getThreeWords.add(allWord.get(3));

                    for(int i=0; i < getThreeWords.size(); i++ )
                    {
                        System.out.println("all three values" + getThreeWords.get(i) + " " +i);

                    }

                    Collections.shuffle(getThreeWords);

                    for(int i=0; i < getThreeWords.size(); i++ )
                    {
                        System.out.println("all three values Shuffled   " + getThreeWords.get(i) + " " +i);

                    }

                    //display words on buttons
                    choice1.setText(getThreeWords.get(0));
                    choice2.setText(getThreeWords.get(1));
                    choice3.setText(getThreeWords.get(2));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    // when clicked on buttons, check if a word is a synonym of the main word
    @Override
    public void onClick(View view) {

        System.out.println("actualt points  " +points);
        switch (view.getId())
        {
            case R.id.word1:
            {


                //might need refactoring
                if(choice1.getText().equals(getSynonym[2]))
                {
                    points = gamesAddon.addPoints(getHelpString);

                    getCorrect= gamesAddon.increaseCorrect();
                    Toast.makeText(getContext(), "Welldone Right Synonym", Toast.LENGTH_SHORT).show();
                    getThreeWords.clear();
                    getRandomWordFromList();
                }
                else
                {
                    if(gamesAddon.getLife() <=0)
                    {
                        Toast.makeText(getContext(), "Incorrect, GameOver", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT).show();

                    getIncorrect =gamesAddon.increaseIncorrect();
                    gamesAddon.removeLife();
                    System.out.println("this is life dog  " + gamesAddon.getLife());
                    recyclerView.setAdapter(adapter);


                }
                break;
            }
            case R.id.word2:
            {

                if(choice2.getText().equals(getSynonym[2]))
                {

                    points = gamesAddon.addPoints(getHelpString);
                    Toast.makeText(getContext(), "Welldone Right Synonym", Toast.LENGTH_SHORT).show();
                    getThreeWords.clear();
                    getRandomWordFromList();

                    gamesAddon.addPoints(getHelpString);
                    getCorrect= gamesAddon.increaseCorrect();
                    System.out.println("This is points" + gamesAddon.getPoints());
                }
                else
                {
                    if(gamesAddon.getLife() == 0)
                    {
                        Toast.makeText(getContext(), "Incorrect, GameOver", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                    getIncorrect =gamesAddon.increaseIncorrect();
                    gamesAddon.removeLife();
                    System.out.println("this is life dog  " + gamesAddon.getLife());
                    recyclerView.setAdapter(adapter);
                }

                break;
            }
            case R.id.word3:
            {

                points = gamesAddon.addPoints(getHelpString);
                if(choice3.getText().equals(getSynonym[2]))
                {
                    getCorrect= gamesAddon.increaseCorrect();
                    Toast.makeText(getContext(), "Welldone Right Synonym", Toast.LENGTH_SHORT).show();
                    getThreeWords.clear();
                    getRandomWordFromList();
                }
                else
                {

                    getIncorrect =gamesAddon.increaseIncorrect();
                    if(gamesAddon.getLife() <= 0)
                    {
                        Toast.makeText(getContext(), "Incorrect, GameOver", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT).show();


                    gamesAddon.removeLife();


                    System.out.println("this is life  " + gamesAddon.getLife());

                    recyclerView.setAdapter(adapter);
                }
                break;
            }

            //handle multiple view click events
        }
    }
}
