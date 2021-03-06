package almeida.john.vocabnote.almieda.john.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by John on 08/11/2017.
 */




public class CardListVocabFragment extends Fragment {

    public List<Classification> UserClass = new ArrayList<>();
    public LinkedList<String> allClass =  new LinkedList<String>();

    public  List<Classification> getdata =  new ArrayList<>();
    public  List<WordsList> getWordList = new ArrayList<>();
    public  RecyclerView recyclerView;
    String[] ClassList ;
    String[] WordList;
    ContentAdapter adapter;
    boolean Category = true;
    FloatingActionButton addCat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View drawer = inflater.inflate(R.layout.recycler_viewtowordandcat, container, false);

        recyclerView = (RecyclerView) drawer.findViewById(R.id.recyclerV);

        bindCategoryOrWordsToRecyclerView();

        addCat = (FloatingActionButton) drawer.findViewById(R.id.fab);

        return drawer;


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


                ClassList = new String[UserClass.size()];
                WordList = new String[getWordList.size()];
                //Log.e("getClass", UserWords.toString());

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < UserClass.size(); i++) {

                    ClassList[i] = UserClass.get(i).getClassification();
                    allClass.addFirst(UserClass.get(i).getClassification());


                }
                for(int i = 0; i <getWordList.size(); i++)
                {
                    WordList[i] = getWordList.get(i).getWord();
                }



                // add all categories to adapater and set recycler view
                adapter = new ContentAdapter(allClass);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {

            }
        });


    }




//    //try to make this an interface
//    private void getUserLists() {
//
//    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.card_list, parent, false));


            description = (TextView) itemView.findViewById(R.id.card_text);





        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder>  {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private  LinkedList<String> Classifications;




        public ContentAdapter(LinkedList<String> getdata) {

            this.Classifications = getdata;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            //display data on the screen
            final String selectedCategory = Classifications.get(position % Classifications.size());
            holder.description.setText(Classifications.get(position % Classifications.size()));





            // set add icon onclilistener
            addCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());

                    View mView = getLayoutInflater().inflate(R.layout.addword, null);

                    final EditText Text = (EditText) mView.findViewById(R.id.addword);



                    //alert dialgo to enter word
                    mBuilder.setPositiveButton("         Save!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if(!Text.getText().toString().isEmpty())
                            {

                                Classifications.addFirst(Text.getText().toString());
                                adapter.notifyDataSetChanged();

                            }

                        }
                    });

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
            });
            //onLongClick
            //setOnclickListener
            holder.description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Toast.makeText(getContext(),selectedCategory,Toast.LENGTH_SHORT).show();

                    String category = selectedCategory;
                    //Fetch lists of users, classifications and Words.
                    Intent cattoWord = new Intent(getActivity(), WordActivity.class);

                    cattoWord.putExtra("Category", category);
                    // getProfileInformationFacebook(loginResult.getAccessToken());
                    startActivity(cattoWord);


                }
            });
            //set on longClickListener
            holder.description.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // TODO Auto-generated method stub

                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            getContext());
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here

                            Toast.makeText(getContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
                            Classifications.remove(selectedCategory);
                            adapter.notifyDataSetChanged();

                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();


                    return true;
                }
            });




        }
        @Override
        public int getItemCount() {
            return Classifications.size();
        }


    }
}