package almeida.john.vocabnote.almieda.john.fragments;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import almeida.john.vocabnote.R;

public class DicActivity extends AppCompatActivity {





    public RecyclerView recyclerView;
    public List<DicInfo> getDicdata =  new ArrayList<>();
    int ListSize = 0;
    TextView Def;
    TextView Example;
    ImageView Pronunciation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic);

        Def = (TextView)findViewById(R.id.def_text);
        Example=  (TextView)findViewById(R.id.examp_text);


        new CallbackTask().execute(dictionaryEntries());
    }




    private String dictionaryEntries() {
        final String language = "en";
        final String word = "car";
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

            String def = null;

            String example = null;






            try {
                JSONObject js = new JSONObject(result);
                JSONArray results = js.getJSONArray("results");




                for(int i = 0; i<results.length(); i++){
                    JSONObject lentries = results.getJSONObject(i);
                    JSONArray la = lentries.getJSONArray("lexicalEntries");
                    for(int j=0;j<la.length();j++){
                        JSONObject entries = la.getJSONObject(j);
                        JSONArray e = entries.getJSONArray("entries");
                        for(int k=0;k<e.length();k++){
                            JSONObject senses = e.getJSONObject(k);
                            JSONArray s = senses.getJSONArray("senses");

                            for(int h = 0; h < s.length(); h++)
                            {
                                JSONObject d = s.getJSONObject(h);
                                JSONArray de = d.getJSONArray("definitions");

                                System.out.println(de);

                                JSONArray examples = d.getJSONArray("examples");

                                def = de.getString(ListSize);
                                example = examples.getString(ListSize);


                                // System.out.println(example);
                                DicInfo dicInfo = new DicInfo(def,example,"prr");


                                getDicdata.add(dicInfo);

                            }





                        }


                    }
                }

                for(int i =0; i < getDicdata.size(); i++)
                {
                    System.out.println(getDicdata.get(i).getDefinitions());
                    System.out.println(getDicdata.get(i).getExample());


                    Def.setText(getDicdata.get(i).getDefinitions());
                    Example.setText(getDicdata.get(i).getExample());
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
