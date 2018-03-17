package almeida.john.vocabnote.almieda.john.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
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


public class GuessWordGameFragment extends Fragment {



    //Override
    public List<Classification> UserClass = new ArrayList<>();
    public  List<Classification> getdata =  new ArrayList<>();
    public  List<WordsList> getWordList = new ArrayList<>();
    public  LinkedList<String> getSplit =   new LinkedList<String>();

    public LinkedList<String> allWord =    new LinkedList<String>();


    public RecyclerView recyclerView;
    String[] ClassList ;
    String[] WordList;
    String[] splitWord;

    ArrayList <String> getThreeWords = new ArrayList<>();
    TextView ChosenWord, choice1, choice2, choice3;

    String Dict;

    String Category;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.WordGamePadding);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        bindCategoryOrWordsToRecyclerView();
        return recyclerView;
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



        for(int i = 0; i <splitWord.length; i++)
        {
            System.out.println("Word Split  " + splitWord[i]);

            getSplit.addFirst(splitWord[i]);
        }



        Collections.shuffle(getSplit);



        GuessWordGameFragment.ContentAdapter adapter = new GuessWordGameFragment.ContentAdapter(getSplit);
        recyclerView.setAdapter(adapter);

    }










    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_guess_word_game, parent, false));


            description = (TextView) itemView.findViewById(R.id.letter);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Context context = v.getContext();
                    //Intent intent = new Intent(context, WordFragment.class);
                    //intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    //context.startActivity(intent);
                }
            });




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
        public void onBindViewHolder(ViewHolder holder, int position) {

            final String selectedCategory = Classifications.get(position % Classifications.size());



            holder.description.setText(Classifications.get(position % Classifications.size()));

            holder.description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),selectedCategory,Toast.LENGTH_SHORT).show();
                }
            });


        }




        @Override
        public int getItemCount() {
            return Classifications.size();
        }
    }
}
