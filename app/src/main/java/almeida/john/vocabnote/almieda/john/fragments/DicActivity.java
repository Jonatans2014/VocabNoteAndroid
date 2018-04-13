package almeida.john.vocabnote.almieda.john.fragments;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import almeida.john.vocabnote.R;

public class DicActivity extends AppCompatActivity {
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


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public  RecyclerView recyclerView;
    public  String[] ClassList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic);

        Def = (TextView)findViewById(R.id.def_text);
        Example=  (TextView)findViewById(R.id.examp_text);

        //get category clicked by users.
        DictWord = getIntent().getStringExtra("Dict");
        System.out.println(DictWord);

        recyclerView =
                (RecyclerView) findViewById(R.id.my_recycler_view);



        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ClassList = new String[2];

        new CallbackTask().execute(dictionaryEntries());

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // use a linear layout manager

//
//        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView example;
        public TextView description;
        public TextView word;
        public ImageView pronunciation;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.dictcardlist, parent, false));


            description = (TextView) itemView.findViewById(R.id.def);
            example  = (TextView) itemView.findViewById(R.id.examp);

            word = (TextView) itemView.findViewById(R.id.wordTV);

            pronunciation = (ImageView) itemView.findViewById(R.id.Pronunciation);


        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<DicActivity.ViewHolder> {
        // Set numbers of List in RecyclerView.

        private  List<DicInfo>  Classifications;

        public  List<Classification> senddata =  new ArrayList<>();

        public ContentAdapter(List<DicInfo> getdata) {

            this.Classifications = getdata;
        }

        @Override
        public DicActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DicActivity.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(DicActivity.ViewHolder holder, int position) {

            final String selectedCategory = Classifications.get(position % Classifications.size()).getDefinitions();

            holder.description.setText( Classifications.get(position % Classifications.size()).getDefinitions());
            holder.example.setText( Classifications.get(position % Classifications.size()).getExample());
            holder.word.setText((position +1)+"." +DictWord);

            holder.pronunciation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Toast.makeText(getApplication(),"vai dar pt",Toast.LENGTH_SHORT).show();

                    MediaPlayer mp = new MediaPlayer();
                    try {
                        mp.setDataSource(Classifications.get(ListSize).getPronuncation());
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
        @Override
        public int getItemCount() {
            return Classifications.size();
        }
    }



    private String dictionaryEntries() {
        final String language = "en";
        final String word = DictWord;
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id ;
    }
    //in android calling network requests on the main thread forbidden by default
    //create class to do async job
    public class CallbackTask extends AsyncTask<String, Integer, String> {

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

                             getDialect = pronunObj.optString("audioFile");

                           // System.out.println("this is file "+getDialect);
                        }


                         lexicalCategory =  entries.optString("lexicalCategory");

                        for(int k=0;k<e.length();k++){
                            JSONObject senses = e.getJSONObject(k);
                            JSONArray s = senses.getJSONArray("senses");

                            for(int h = 0; h < s.length(); h++)
                            {
                                JSONObject d = s.getJSONObject(h);
                                example =  d.optString("examples");

                                def = d.optString("definitions");


                                //get rid of none words and the name text:
                                String replaceNoneWordsExample =example.replaceAll("\\[|\\]|\\\"|\\[", " ");
                                String replaceNoneWordsDefinition =def.replaceAll(" \\[|\\]|\\\"|\\[", " ");

                                DicInfo dicInfo = new DicInfo(replaceNoneWordsDefinition,replaceNoneWordsExample,getDialect);
                                getDicdata.add(dicInfo);

                               // lexicalCategory

                            }


                        }


                    }
                }

//                for(int i =0; i < getDicdata.size(); i++)
//                {
//                    System.out.println("this is all def"+getDicdata.get(i).getDefinitions());
//                    System.out.println(getDicdata.get(i).getExample());
//
//
//                    Def.append(getDicdata.get(i).getDefinitions());
//                    Example.append(getDicdata.get(i).getExample());
//                }

                DicActivity.ContentAdapter adapter = new DicActivity.ContentAdapter(getDicdata);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
