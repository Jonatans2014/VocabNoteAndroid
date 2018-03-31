package almeida.john.vocabnote.almieda.john.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import almeida.john.vocabnote.R;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;


/**
 * Created by John on 27/11/2017.
 */

public class ChatBotFragment extends Fragment implements AIListener ,View.OnClickListener{

    AIService aiService;
    TextView t;
    Button activate;
    public RecyclerView recyclerView;
    EditText editText;
    RelativeLayout addBtn;
    AIRequest aiRequest;
    AIDataService aiDataService;
    Boolean flagFab = true;

    ImageView fab_img;
    LinkedList<String> msg = new LinkedList<>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


        final View drawer = inflater.inflate(R.layout.chatbotrecyclerview, container, false);

        recyclerView =  drawer.findViewById(R.id.recyclerView);
        editText = (EditText)drawer.findViewById(R.id.editText);
        addBtn = (RelativeLayout)drawer.findViewById(R.id.addBtn);
        final ImageView fab_img = (ImageView) drawer.findViewById(R.id.fab_img);

        msg.addLast("hey how are you?");
        msg.addLast("fine thanks");




        // dialogflow declaration


        final AIConfiguration config = new AIConfiguration("c96a55a373104d3b84f28ee97662131e",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(getContext(), config);
        aiService.setListener(this);

        aiDataService = new AIDataService(config);
        aiRequest = new AIRequest();




        addBtn.setOnClickListener(this);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.ic_send_white_24dp);
                Bitmap img1 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_mic_white_24dp);


                if (s.toString().trim().length()!=0 && flagFab){
                    ImageViewAnimatedChange(getContext(),fab_img,img);
                    flagFab=false;

                }
                else if (s.toString().trim().length()==0){
                    ImageViewAnimatedChange(getContext(),fab_img,img1);
                    flagFab=true;

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ContentAdapter adapter = new ContentAdapter(msg);
        recyclerView.setHasFixedSize(true);
//        // Set padding for Tiles
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);




        return drawer;

    }
    public void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.zoom_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, R.anim.zoom_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    @Override
    public void onClick(View view) {


        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.addBtn: {


                String message = editText.getText().toString().trim();

                if (!message.equals("")) {

                    ChatMessage chatMessage = new ChatMessage(message, "user");
                   // ref.child("chat").push().setValue(chatMessage);

                    aiRequest.setQuery(message);
                    new AsyncTask<AIRequest,Void,AIResponse>(){

                        @Override
                        protected AIResponse doInBackground(AIRequest... aiRequests) {
                            final AIRequest request = aiRequests[0];
                            try {
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }
                        @Override
                        protected void onPostExecute(AIResponse response) {
                            if (response != null) {

                                Result result = response.getResult();


                                String reply = result.getFulfillment().getSpeech();
                                ChatMessage chatMessage = new ChatMessage(reply, "bot");


                                //ref.child("chat").push().setValue(chatMessage);

                                System.out.println("work work work work work " + result +"  " + reply );
                            }
                        }
                    }.execute(aiRequest);
                }
                else {
                    aiService.startListening();
                }

                editText.setText("");



                break;

            }
        }
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView sender,reciever;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_chatbot, parent, false));


            sender = (TextView) itemView.findViewById(R.id.leftText);
            //reciever = (TextView) itemView.findViewById(R.id.letter);


        }
    }




    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ChatBotFragment.ViewHolder> {
        // Set numbers of List in RecyclerView.

        private LinkedList<String> Classifications;




        public List<Classification> senddata =  new ArrayList<>();

        public ContentAdapter(LinkedList<String> getdata) {

            this.Classifications = getdata;


        }





        @Override
        public ChatBotFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ChatBotFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);


        }

        @Override
        public void onBindViewHolder(final ChatBotFragment.ViewHolder holder, int position) {

            final String selectedCategory = Classifications.get(position % Classifications.size());
            holder.sender.setText(Classifications.get(position % Classifications.size()));
           // holder.reciever.setText(Classifications.get(position % Classifications.size()));
        }
        @Override
        public int getItemCount() {
            return Classifications.size();
        }
    }

    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }


}

