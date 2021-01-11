package com.example.virtualvolunteer.SearchPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.virtualvolunteer.ProfilePage.Profile;
import com.example.virtualvolunteer.R;
import com.example.virtualvolunteer.Upload;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<SearchResult> searchResults, searchResultsTemp;
    CustomFilter filter;

    public SearchAdapter(Context context, ArrayList<SearchResult> searchResults) {
        this.context = context;
        this.searchResults = searchResults;
        this.searchResultsTemp = searchResults;
    }

    @Override
    public Object getItem(int i) {
        return searchResults.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.search_item, null);

        TextView name = row.findViewById(R.id.search_name);
        ImageView image = row.findViewById(R.id.search_profile_image);
        TextView bio = row.findViewById(R.id.search_bio);
        TableRow result = row.findViewById(R.id.search_result);

        name.setTypeface(null, Typeface.BOLD);

        result.setOnClickListener(v -> {
            // TODO: when user clicks search result box, view user's profile page
            viewProfile(searchResults.get(i).getEmail());

        });

        name.setText(searchResults.get(i).getName());
        Upload upload = searchResults.get(i).getImageURL();
        if (upload != null) {
            Picasso.with(context).load(upload.getImageUrl()).resize(200, 200).centerCrop().into(image);
        }
        bio.setText(searchResults.get(i).getBio());

        return row;
    }

    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<SearchResult> filters = new ArrayList<>();

                for (int i = 0; i < searchResults.size(); i++) {
                    if (searchResultsTemp.get(i).getName().toUpperCase().contains(constraint)) {
                        SearchResult sr = new SearchResult(searchResults.get(i).getEmail(), searchResultsTemp.get(i).getName(), searchResultsTemp.get(i).getBio(), searchResultsTemp.get(i).getImageURL());
                        filters.add(sr);
                    }
                }

                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = searchResultsTemp.size();
                results.values = searchResultsTemp;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            searchResults = (ArrayList<SearchResult>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public void viewProfile(String email) {
        Intent intent = new Intent(context, Profile.class);
        Bundle bundle = new Bundle();
        bundle.putString("Email", email);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
