package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import almeida.john.vocabnote.R;


public class ChatbotGridFragment extends Fragment {

    String Chatbotfrag =  "chatbotfrag";
    String message;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View drawer = inflater.inflate(R.layout.recycler_view, container, false);



        recyclerView = (RecyclerView)  drawer.findViewById(R.id.recyclerV);

        ChatbotGridFragment.ContentAdapter adapter = new ChatbotGridFragment.ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return drawer;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {

            // this code to be used to connect the fragments
            super(inflater.inflate(R.layout.fragment_chatbot_grid, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.profile_image);
            name = (TextView) itemView.findViewById(R.id.lesson);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
//                    context.startActivity(intent);
                }
            });
        }}

    /**
     * Adapter to display recycler view.
     */
    public  class ContentAdapter extends RecyclerView.Adapter<ChatbotGridFragment.ViewHolder> {
        // Set numbers of Tiles in RecyclerView.
        private static final int LENGTH = 2;
        private final Drawable[] mPlacePictures;
        private final String[] mPlaces ;
        TypedArray a;
        GamesAddon gamesAddon = new GamesAddon();

        //      //  private final String[] mPlaces;
//        //private final Drawable[] mPlacePictures;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();

            mPlaces = resources.getStringArray(R.array.lessons);


            if(gamesAddon.getOverAllScore() >= gamesAddon.getLvl2())
            {
                a = resources.obtainTypedArray(R.array.chatbotlevel2);
            }
            else
            {
                a = resources.obtainTypedArray(R.array.chatbotlevel1);
            }

            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ChatbotGridFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ChatbotGridFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ChatbotGridFragment.ViewHolder holder, final int position) {

//            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
            holder.name.setText(mPlaces[position % mPlaces.length]);
            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(position == 0)
                    {
                        Intent intent = new Intent(getContext(), SynonymGameActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("fragment", Chatbotfrag);
                        startActivity(intent);

                    }else if(position == 1 && gamesAddon.getOverAllScore() >= gamesAddon.getLvl2())
                    {
                        Intent intent = new Intent(getContext(), SynonymGameActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("fragment", Chatbotfrag);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Please progress to level2 to unlock this conversation",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        @Override
        public int getItemCount(


        ) {
            return LENGTH;
        }
    }
}
