package edu.qc.seclass.glm.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;

import edu.qc.seclass.glm.GroceryListDatabase;
import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.adapters.viewHolders.GroceryItemViewHolder;
import edu.qc.seclass.glm.entities.GroceryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GroceryItemsAdapter extends BaseAdapter implements View.OnClickListener, Filterable {

    private Activity activity;
    private List<GroceryItem> groceryItems;
    private List<GroceryItem> displayedItems;

    public GroceryItemsAdapter(Activity activity, List<GroceryItem> groceryItems) {
        this.activity = activity;
        this.groceryItems = groceryItems;
        this.displayedItems = this.groceryItems;
    }

    @Override
    public int getCount() {
        return displayedItems.size();
    }

    @Override
    public Object getItem(int i) {
        return displayedItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return displayedItems.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        GroceryItemViewHolder viewHolder;

        if (view == null) {

            view = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.grocery_item, viewGroup, false);

            viewHolder = new GroceryItemViewHolder(i, view);

            view.setTag(viewHolder);

        } else
            viewHolder = (GroceryItemViewHolder) view.getTag();

        GroceryItem groceryItem = displayedItems.get(i);

        viewHolder.name.setText(groceryItem.name);
        viewHolder.itemType.setText(groceryItem.itemType.getName());
        viewHolder.amount.setText(String.format(Locale.US, "%d", groceryItem.amount));
        viewHolder.isChecked.setChecked(groceryItem.isChecked);
        viewHolder.isChecked.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {

        GroceryItemViewHolder viewHolder = (GroceryItemViewHolder) ((View) view.getParent()).getTag();
        int index = viewHolder.index;
        GroceryItem groceryItem = groceryItems.get(index);
        groceryItem.isChecked = ((CheckBox) view).isChecked();

        GroceryListDatabase.getInstance(view.getContext()).getGroceryItemDao().updateGroceryItem(groceryItem);

    }

    public List<GroceryItem> getGroceryItems() {
        return displayedItems;
    }

    public boolean contains(String name) {

        for (GroceryItem item : groceryItems)
            if (item.name.equalsIgnoreCase(name) || item.name.toLowerCase().contains(name.toLowerCase()) || name.toLowerCase().contains(item.name.toLowerCase()))
                return true;

        return false;

    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults results = new FilterResults();
                List<GroceryItem> filteredItems = new ArrayList<>();
                String constraint = charSequence.toString().toLowerCase();

                if (constraint.isEmpty())
                    filteredItems = groceryItems;
                else {

                    for (GroceryItem item : groceryItems)
                        if (item.name.toLowerCase().contains(constraint) ||
                                item.itemType.getName().toLowerCase().contains(constraint))
                            filteredItems.add(item);

                }

                results.count = filteredItems.size();
                results.values = filteredItems;

                return results;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                displayedItems = (List<GroceryItem>) filterResults.values;
                notifyDataSetChanged();

            }

        };

    }
}
