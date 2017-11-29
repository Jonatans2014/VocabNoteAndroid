package almeida.john.vocabnote.almieda.john.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import almeida.john.vocabnote.Classification;
import almeida.john.vocabnote.LoginActivity;
import almeida.john.vocabnote.R;

/**
 * Created by John on 08/11/2017.
 */

public class CardListVocabFragment extends Fragment {

    public  List<Classification> UserClass = new ArrayList<>();
    public String getUserClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public List getList(List<Classification> getC)
    {
        UserClass =  getC;
        for(int i = 0; i < UserClass.size(); i++)
        {

            getUserClass = UserClass.get(i).getClassification().toString();
            Log.e("getLs",getUserClass);
        }

        return getC;
    }

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
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private final String[] Classifications;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            Classifications =resources.getStringArray(R.array.place_desc);}

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


            holder.description.setText(Classifications[position % Classifications.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }





}
