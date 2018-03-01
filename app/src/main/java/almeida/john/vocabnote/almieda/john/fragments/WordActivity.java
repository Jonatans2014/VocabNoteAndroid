package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import almeida.john.vocabnote.Api;
import almeida.john.vocabnote.LoginActivity;
import almeida.john.vocabnote.MainActivity;
import almeida.john.vocabnote.R;
import almeida.john.vocabnote.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordActivity extends AppCompatActivity {




    public List<Classification> UserClass = new ArrayList<>();
    private RecyclerView mRecyclerView;
    public  List<Classification> getdata =  new ArrayList<>();
    public  List<WordsList> getWordList = new ArrayList<>();
    public  RecyclerView recyclerView;
    String[] ClassList ;
    String[] WordList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        initViews();
        bindCategoryOrWordsToRecyclerView();





    }




    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

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


                    getWordList  = UserClass.get(0).getWord();
                }

                //  System.out.println(UserWords);

                // WordList is now working try the same with another fragment

                ClassList = new String[UserClass.size()];
                WordList = new String[getWordList.size()];
                //Log.e("getClass", UserWords.toString());

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < UserClass.size(); i++) {

                    ClassList[i] = UserClass.get(i).getClassification();


                }
                for(int i = 0; i <getWordList.size(); i++)
                {
                    WordList[i] = getWordList.get(i).getWord();
                }






                WordActivity.ContentAdapter adapter = new WordActivity.ContentAdapter(WordList);
                mRecyclerView.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {

            }
        });


    }






    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.cardword_row, parent, false));


            description = (TextView) itemView.findViewById(R.id.word);


        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<WordActivity.ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private  String[] Classifications;

        public List<Classification> senddata =  new ArrayList<>();

        public ContentAdapter(String[] getdata) {

            this.Classifications = getdata;
        }

        @Override
        public WordActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WordActivity.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(WordActivity.ViewHolder holder, int position) {

            final String selectedCategory = Classifications[position % Classifications.length];



            holder.description.setText(Classifications[position % Classifications.length]);

            holder.description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //Fetch lists of users, classifications and Words.
                    Intent fbdata = new Intent(WordActivity.this, DicActivity.class);
                    // getProfileInformationFacebook(loginResult.getAccessToken());
                    startActivity(fbdata);


                }
            });


        }







        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }








    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

//                if (mAdapter != null) mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


}
