package edu.qc.seclass.glm.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.adapters.viewHolders.GroceryListsViewHolder;
import edu.qc.seclass.glm.entities.GroceryList;

import java.util.List;

public class GroceryListsAdapter extends BaseAdapter {

    private Activity activity;
    private List<GroceryList> groceryLists;

    public GroceryListsAdapter(Activity activity, List<GroceryList> groceryLists) {
        this.activity = activity;
        this.groceryLists = groceryLists;
    }

    @Override
    public int getCount() {
        return groceryLists.size();
    }

    @Override
    public Object getItem(int i) {
        return groceryLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return groceryLists.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        GroceryListsViewHolder viewHolder;

        if (view == null) {

            view = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.grocery_list_item, viewGroup, false);

            viewHolder = new GroceryListsViewHolder(view);

            view.setTag(viewHolder);

        } else
            viewHolder = (GroceryListsViewHolder) view.getTag();

        viewHolder.name.setText(groceryLists.get(i).name);

        return view;

    }

    public List<GroceryList> getGroceryLists() {
        return groceryLists;
    }

    public void setGroceryLists(List<GroceryList> groceryLists) {
        this.groceryLists = groceryLists;
    }

}
