package xyz.lgvalle.materialdoc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by lgvalle on 25/01/16.
 */
public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogsViewHolder> implements Filterable {

    private final LayoutInflater layoutInflater;
    private String[] dogs = {"Schanutzer", "Collie", "Dogo", "Labrador"};
    List<String> filteredDogs;

    public DogsAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        filteredDogs = new ArrayList<>();
    }

    @Override
    public DogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = layoutInflater.inflate(R.layout.row_dog_item, parent, false);
        return new DogsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DogsViewHolder holder, int position) {
        holder.bind(dogs[position]);
    }


    @Override
    public int getItemCount() {
        return dogs.length;
    }

    @Override
    public Filter getFilter() {
        return new DogsFilter(this, dogs);
    }

    public class DogsViewHolder extends RecyclerView.ViewHolder {

        private TextView name;

        public DogsViewHolder(View itemView) {
            super(itemView);
            //name = (TextView) itemView.findViewById(R.id.row_name);
        }


        public void bind(String dog) {
            name.setText(dog);
        }
    }

    private class DogsFilter extends Filter {

        DogsAdapter adapter;
        String[] originalList;
        List<String> filteredList;

        public DogsFilter(DogsAdapter adapter, String[] originalList) {
            super();
            this.adapter = adapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                Collections.addAll(filteredList, originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final String dog : originalList) {
                    if (dog.contains(filterPattern)) {
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
            adapter.filteredDogs.addAll((Collection<? extends String>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
