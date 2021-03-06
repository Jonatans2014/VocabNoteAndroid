package almeida.john.vocabnote.almieda.john.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import javax.net.ssl.HttpsURLConnection;

import almeida.john.vocabnote.Api;
import almeida.john.vocabnote.R;
import almeida.john.vocabnote.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SynonymGamePlayActivity extends AppCompatActivity  implements View.OnClickListener {



    public List<Classification> UserClass = new ArrayList<>();
    public  List<Classification> getdata =  new ArrayList<>();
    public  List<WordsList> getWordList = new ArrayList<>();

    public  List<String> Tryhard = new ArrayList<>();
    public LinkedList<String> allWord =  new LinkedList<String>();

    public LinkedList<List<WordsList>> lK = new LinkedList<List<WordsList>>();
    public RecyclerView recyclerView;
    String[] ClassList ;
    String[] WordList;
    static int counter;
    String [] getSynonym;
    int SynonymObjectSize = 1;
    ArrayList <String> getThreeWords = new ArrayList<>();
    TextView ChosenWord, choice1, choice2, choice3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonym_game_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bindCategoryOrWordsToRecyclerView();



        ChosenWord = (TextView) findViewById(R.id.mainWord);

        choice1 = (TextView) findViewById(R.id.word1);

        choice2 = (TextView) findViewById(R.id.word2);
        choice3 = (TextView) findViewById(R.id.word3);



        //set onClick Listener
         choice1.setOnClickListener(this);
         choice2.setOnClickListener(this);
         choice3.setOnClickListener(this);



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


//        if(mainWord == otherword)
//        {
//            otherword =  r.nextInt(max - min) + min;
//        }
//        allWord.size();


//        System.out.println("this is values of linkedlist in get random"+  allWord.get(mainWord) + " plus " + "Random Number" + mainWord + "other word" + otherword);


        new CallbackTask().execute(dictionaryEntries(allWord.getFirst()));




    }


    //dictionary Entries
    private String dictionaryEntries(String getWord) {
        final String language = "en";
        final String word = allWord.getFirst();
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


        switch (view.getId())
        {
            case R.id.word1:
            {

                if(choice1.getText().equals(getSynonym[2]))
                {
                    Toast.makeText(getApplicationContext(), "Welldone Right Synonym", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.word2:
            {

                if(choice2.getText().equals(getSynonym[2]))
                {
                    Toast.makeText(getApplicationContext(), "Welldone Right Synonym", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.word3:
            {


                if(choice3.getText().equals(getSynonym[2]))
                {
                    Toast.makeText(getApplicationContext(), "Welldone Right Synonym", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                }
                break;
            }

              //handle multiple view click events
        }
    }

}


