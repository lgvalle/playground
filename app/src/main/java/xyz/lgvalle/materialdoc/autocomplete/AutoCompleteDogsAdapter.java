package xyz.lgvalle.materialdoc.autocomplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.lgvalle.materialdoc.R;

/**
 * Created by lgvalle on 04/02/16.
 */
public class AutoCompleteDogsAdapter extends ArrayAdapter<Dog> {

    private final List<Dog> dogs;
    private List<Dog> filteredDogs = new ArrayList<>();

    public AutoCompleteDogsAdapter(Context context, List<Dog> dogs) {
        super(context, 0, dogs);
        this.dogs = dogs;
    }


    @Override
    public int getCount() {
        return filteredDogs.size();
    }


    @Override
    public Filter getFilter() {
        return new DogsFilter(this, dogs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Dog dog = filteredDogs.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_autocomplete, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.row_title);
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.row_icon);
        tvName.setText(dog.breed);
        ivIcon.setImageResource(dog.drawable);
        return convertView;
    }

    private class DogsFilter extends Filter {

        AutoCompleteDogsAdapter adapter;
        List<Dog> originalList;
        List<Dog> filteredList;

        public DogsFilter(AutoCompleteDogsAdapter adapter, List<Dog> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final Dog dog : originalList) {
                    if (dog.breed.toLowerCase().contains(filterPattern)) {
                        filteredList.add(dog);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredDogs.clear();
            adapter.filteredDogs.addAll((List) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
