package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;


public class GamesFragment extends Fragment {

    String gamefrag =  "gamesfrag";

    RecyclerView recyclerView;

    public  static final  String SHARED_PREFS = "sharedprefs";
    public String Text =" TEXT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View drawer = inflater.inflate(R.layout.recycler_view, container, false);



        recyclerView = (RecyclerView)  drawer.findViewById(R.id.recyclerV);




        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
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
            super(inflater.inflate(R.layout.fragment_games, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.profile_image);
            name = (TextView) itemView.findViewById(R.id.game);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
//                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public  class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Tiles in RecyclerView.
        private static final int LENGTH = 2;
       private final Drawable[] mPlacePictures;
        private final String[] mPlaces;

        GamesAddon gamesAddon = new GamesAddon();
//        //private final Drawable[] mPlacePictures;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.games);
            TypedArray a;


       System.out.println("this is overal  " +gamesAddon.getLvl2());

            if(gamesAddon.getOverAllScore() >= gamesAddon.getLvl2())
            {
                a = resources.obtainTypedArray(R.array.gameslevel2);
            }
            else
            {
                 a = resources.obtainTypedArray(R.array.gameslevel1);
            }

            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            String selectedCategory;

            // holder.picture.setImageDrawable();
            holder.name.setText(mPlaces[position % mPlaces.length]);
            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        System.out.println(position);
                        if(position == 0)
                        {
                            Intent intent = new Intent(getContext(), SynonymGameActivity.class);
                            intent.putExtra("position", position);
                            intent.putExtra("fragment", gamefrag);
                            startActivity(intent);

                        }else if(position == 1 && gamesAddon.getOverAllScore() >= gamesAddon.getLvl2())
                        {
                            Intent intent = new Intent(getContext(), SynonymGameActivity.class);
                            intent.putExtra("position", position);
                            intent.putExtra("fragment", gamefrag);
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

