package almeida.john.vocabnote.almieda.john.fragments;

import android.content.Context;
import android.content.Intent;
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

import almeida.john.vocabnote.R;


public class ChatbotGridFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);


        ChatbotGridFragment.ContentAdapter adapter = new ChatbotGridFragment.ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {

            // this code to be used to connect the fragments
            super(inflater.inflate(R.layout.fragment_chatbot_grid, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            name = (TextView) itemView.findViewById(R.id.tile_title);
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

        public ContentAdapter(Context context) {
        }

        @Override
        public ChatbotGridFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ChatbotGridFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ChatbotGridFragment.ViewHolder holder, final int position) {

//            holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
//            holder.name.setText(mPlaces[position % mPlaces.length]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    System.out.println(position);

                    Intent intent = new Intent(getContext(), SynonymGameActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("fragment", "chatbot");
                    startActivity(intent);



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