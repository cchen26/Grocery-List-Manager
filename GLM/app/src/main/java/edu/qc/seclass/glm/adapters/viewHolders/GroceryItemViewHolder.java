package edu.qc.seclass.glm.adapters.viewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import edu.qc.seclass.glm.R;

public class GroceryItemViewHolder {

    public int index;

    public TextView name;
    public TextView itemType;
    public TextView amount;
    public CheckBox isChecked;

    public GroceryItemViewHolder(int index, View view) {

        this.index = index;
        this.name = view.findViewById(R.id.grocery_item_name);
        this.itemType = view.findViewById(R.id.grocery_item_type);
        this.amount = view.findViewById(R.id.grocery_item_amount);
        this.isChecked = view.findViewById(R.id.grocery_item_checkbox);

    }

}
