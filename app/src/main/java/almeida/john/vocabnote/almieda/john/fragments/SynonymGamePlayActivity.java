package almeida.john.vocabnote.almieda.john.fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import almeida.john.vocabnote.Api;
import almeida.john.vocabnote.R;
import almeida.john.vocabnote.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SynonymGamePlayActivity extends AppCompatActivity {



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonym_game_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bindCategoryOrWordsToRecyclerView();

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
                        counter ++;
                    }



                        //System.out.println( "this is userclass"+UserClass.get(i).getWord().toString());

                }


                System.out.println("this is linkedlist"+allWord.size());


                   for(int i=0; i < allWord.size(); i++)
                   {
                       System.out.println("this is values of linkedlist"+allWord.get(i));
                   }




//
//                for(int i = 0; i <getWordList.size(); i++)
//                {
//                    WordList[i] = getWordList.get(i).getWord();
//
//                    System.out.println("Words"+ WordList[i] );
//                }




            }


            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {

            }
        });



    }

}
