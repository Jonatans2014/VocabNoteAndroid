package almeida.john.vocabnote;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG =" GotThe msg" ;
    LoginButton loginButtonFB;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getHeroes();



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

                getProfileInformationFacebook(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }





    private void getHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<UserInfo>> call = api.getHeroes();

        call.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {


                List<UserInfo> users =  response.body();


                // arraylist for the wordlist
                List<WordList> UserInfo = new ArrayList<>();

                for(int i = 0; i < users.size(); i ++)
                {
                    UserInfo = users.get(i).getWordList();
                }

                for (int i = 0; i < UserInfo.size(); i++)
                {
                    Log.e("UserInfo", UserInfo.get(i).getWord());
                }


                //Log.e("Users", users.toString());




//                List<Users> heroList = response.body();
//
//                //Creating an String array for the ListView
//                List<Users> heroes = new ArrayList<>();
//
//                Log.e("Student name", heroes.toString());
//               // looping through all the heroes and inserting the names inside the string array
////                for (int i = 0; i < heroList.size(); i++) {
////                    heroes[i] = heroList.get(i).getUser_Name();
////
////                    System.out.println("hiiii"+heroes[i]);
////
//
//                }


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
                        Log.e("object", object.toString());
                        String fbId = null;
                        String fbBirthday = null;
                        String fbLocation = null;
                        String fbEmail = null;
                        String fbName = null;
                        String fbGend = null;


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
                            }

                            //fbPropic = "https://graph.facebook.com/\"+ fbId +\"/picture?type=small";

                          //  session.FbLogindata(fbId, fbName, fbPropic, fbLocation, fbGend, fbEmail);


                            Intent fbdata = new Intent(LoginActivity.this, MainActivity.class);

                            /*
                            fbdata.putExtra("fbid",fbId);
                            fbdata.putExtra("fbname",fbName);
                            fbdata.putExtra("email",fbEmail);
                            fbdata.putExtra("gender",fbGend);
                            fbdata.putExtra("location",fbLocation);*/

                           // main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
                            startActivity(fbdata);



                            Log.d("lastName",fbName);
                            Log.d("email",fbName);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,location,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
