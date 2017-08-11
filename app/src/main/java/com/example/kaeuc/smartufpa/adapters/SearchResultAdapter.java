package com.example.kaeuc.smartufpa.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kaeuc.smartufpa.R;
import com.example.kaeuc.smartufpa.models.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaeuc on 11/14/2016.
 * ArrayAdapter para ser utilizado na lista de resultados da Busca
 */

public class SearchResultAdapter extends RecyclerView.Adapter{

    private List<Place> places;
    private Context context;

    public SearchResultAdapter(List<Place> places, Context context) {
        this.places = places;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.search_result_item,parent,false);

        SearchResultViewHolder holder = new SearchResultViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SearchResultViewHolder viewHolder = (SearchResultViewHolder) holder;

        Place place = places.get(position);

        viewHolder.txtPlaceName.setText(place.getName() + "(" + place.getShortName() + ")");
        viewHolder.txtLocName.setText(place.getLocName());
        viewHolder.txtPlaceDescription.setText(place.getDescription());

    }

    @Override
    public int getItemCount() {
        return places.size();
    }


    private class SearchResultViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPlaceName, txtLocName,txtPlaceDescription;

        private SearchResultViewHolder(View itemView) {
            super(itemView);

            txtPlaceName = (TextView) itemView.findViewById(R.id.list_txt_place_name);
            txtLocName = (TextView) itemView.findViewById(R.id.list_txt_place_loc_name);
            txtPlaceDescription = (TextView) itemView.findViewById(R.id.list_txt_place_description);
        }
    }
}
