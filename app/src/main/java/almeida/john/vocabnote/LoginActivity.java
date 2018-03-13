package almeida.john.vocabnote;
import android.content.Intent;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import almeida.john.vocabnote.almieda.john.fragments.CardListVocabFragment;
import almeida.john.vocabnote.almieda.john.fragments.Classification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG =" GotThe msg" ;
    LoginButton loginButtonFB;
    CallbackManager callbackManager;
    // List of Classifications
    List<Classification> UserClass = new ArrayList<>();

    List<String>ConStringCLass= new ArrayList<>();
    // List of Words
  //  List<WordsList> UserWords = new ArrayList<>();

    String[] ClassList ;
    String[] ClassWord ;
    ArrayList<String> namesArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Fetch lists of users, classifications and Words.
        getUserLists();


        //Fetch lists of users, classifications and Words.
        Intent fbdata = new Intent(LoginActivity.this, MainActivity.class);
        // getProfileInformationFacebook(loginResult.getAccessToken());
        startActivity(fbdata);

        // findIds
        loginButtonFB = (LoginButton) findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        //Permissions
        loginButtonFB.setReadPermissions("email,","public_profile");
        // Callback registration
        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Intent fbdata = new Intent(LoginActivity.this, MainActivity.class);
                getProfileInformationFacebook(loginResult.getAccessToken());
                startActivity(fbdata);

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), TAG, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(),TAG, Toast.LENGTH_SHORT).show();
            }
        });
    }




    //try to make this an interface
    private void getUserLists() {
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
                }

                ClassList = new String[UserClass.size()];



                //Log.e("getClass", UserWords.toString());

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < UserClass.size(); i++) {
                    ClassList[i] = UserClass.get(i).getClassification();

                    if(UserClass.get(i).getClassification().equals("Fruits"))
                    {
                        System.out.println("Class"+  ClassList[i] );

                    }


                  //  UserWords = UserClass.get(0).getWord();
                }


               // ClassWord = new String[UserWords.size()];








            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("On failure"+getApplicationContext()+ t.getMessage());
            }
        });
    }




    // get Profile Info
    public void getProfileInformationFacebook(AccessToken accToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        //declaring variables
                        String fbId = null;
                        String fbEmail = null;
                        String fbName = null;
                        String fbGend = null;
                        String  fbPropic = null;
                        try {

                            if(object.has("email")){
                                fbEmail = object.getString("email");
                            }
                            else {
                                fbEmail = "";
                            }if(object.has("name")){
                                fbName = object.getString("name");
                            }
                            else {
                                fbName ="";
                            }if(object.has("gender")){
                                fbGend = object.getString("gender");
                            }else {fbGend = "";}
                              fbPropic = "https://graph.facebook.com/\"+ fbId +\"/picture?type=small";






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static class wordFragment extends android.support.v4.app.Fragment {


        public List<Classification> UserClass = new ArrayList<>();

        public  List<Classification> getdata =  new ArrayList<>();
        public RecyclerView recyclerView;
        String[] ClassList ;
        String[] WordList;
        boolean Category = true;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);




            bindCategoryOrWordsToRecyclerView();



            return recyclerView;


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



                    }



                    ClassList = new String[UserClass.size()];
                    WordList = new String[UserClass.get(0).getWord().size()];
                    //Log.e("getClass", UserWords.toString());

                    //looping through all the heroes and inserting the names inside the string array
                    for (int i = 0; i < UserClass.size(); i++) {

                        ClassList[i] = UserClass.get(i).getClassification();

                    }




//
//                    ContentAdapter adapter = new ContentAdapter(ClassList);
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.setHasFixedSize(true);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



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






    }
}
